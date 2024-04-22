package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.EventRequest;
import com.fulltack.zooManagment.exception.EventNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Event convertToEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventID(UUID.randomUUID().toString().split("-")[0]);
        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventLocation(eventRequest.getEventLocation());
        event.setCapacity(eventRequest.getCapacity());
        event.setEventManager("NA");
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
            if (repository.existsById(eventID)) {
                repository.deleteById(eventID);
                return "Event Deleted Successfully";
            } else {
                return "Event doesn't exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Deleting Event", e);
        }
    }

    //TODO: DO
    public String updateEvent(Event eventRequest) {
        try {
            if (repository.existsByEventID(eventRequest.getEventID())) {
                Event existingEvent = repository.findById(eventRequest.getId()).get();
                existingEvent.setEventName(eventRequest.getEventName());
                existingEvent.setEventDescription(eventRequest.getEventDescription());
                existingEvent.setEventLocation(eventRequest.getEventLocation());
                existingEvent.setCapacity(eventRequest.getCapacity());

                //return repository.save(existingEvent);
                return eventRequest.getEventID() + " Ticket Creating Successful";
            } else {
                return eventRequest.getEventID() + " Ticket Creating Unsuccessful";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Updating Ticket", e);
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

}