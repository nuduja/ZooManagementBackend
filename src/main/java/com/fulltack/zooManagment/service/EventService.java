package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.EventRequest;
import com.fulltack.zooManagment.enums.EventStatus;
import com.fulltack.zooManagment.exception.EventNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    public Event convertToEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventID(UUID.randomUUID().toString().split("-")[0]);
        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventLocation(eventRequest.getEventLocation());
        event.setCapacity(eventRequest.getCapacity());
        event.setEventManager("NA");
        event.setStatus(EventStatus.NA);
        event.setUsername(eventRequest.getUsername());
        return event;
    }

    public List<Event> getAllEvents() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Event", e);
        }
    }

    public Event getEventByEventID(String eventID) {
        try {
            Event event = repository.getEventByEventID(eventID);

            if (event == null) {
                throw new EventNotFoundException("Event with ID " + eventID + " not found");
            }
            return event;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching the ticket", e);
        }
    }

    public String addEvent(EventRequest eventRequest) {
        try {
            Event event = convertToEvent(eventRequest);
            if (event.getEventID() == null || event.getEventName() == null || event.getEventDescription() == null || event.getEventLocation() == null || event.getEventDate() == null) {
                throw new IllegalArgumentException("Ticket type and status must be valid.");
            }
            repository.save(event);
            return "Event Creating Successful";
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Creating Event", e);
        }
    }

    public String deleteEventByEventID(String eventID) {
        try {
            if (repository.existsByEventID(eventID)) {
                repository.deleteByEventID(eventID);
                return "Event Deleted Successfully";
            } else {
                return "Event doesn't exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Deleting Event", e);
        }
    }

    public List<Event> searchEvents(String eventID, String eventLocation, String eventManager) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (eventID != null && !eventID.isEmpty()) {
                criteria.add(Criteria.where("eventID").regex(eventID, "i"));
            }
            if (eventLocation != null && !eventLocation.isEmpty()) {
                criteria.add(Criteria.where("eventLocation").regex(eventLocation, "i"));
            }
            if (eventManager != null && !eventManager.isEmpty()) {
                criteria.add(Criteria.where("eventManager").regex(eventManager, "i"));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, Event.class);
        } catch(Exception e){
            throw new ServiceException("Error Searching Event", e);
        }
    }

    public List<Event> getEventsByUsername(String username) {
        try {
            return repository.findByUsername(username);
        } catch(Exception e){
            throw new ServiceException("Error fetching Events", e);
        }
    }

    public void updateEventManager(String eventID, String eventManager) {
        repository.findByEventID(eventID)
                .ifPresent(event -> {
                    event.setEventManager(eventManager);
                    repository.save(event);
                });
    }

    public ByteArrayInputStream generateEventsPDF() {
        List<Event> events = repository.findAll();
        return pdfGeneratorService.eventReport(events);
    }

    public void updateEventByEventId(String eventID, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("eventID").is(eventID));
        Update update = new Update();
        updates.forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("eventID")) {
                update.set(key, value);
            }
        });
        mongoTemplate.findAndModify(query, update, Event.class);
    }

}