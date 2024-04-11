package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.repository.EventRepository;
import com.sun.java.accessibility.util.EventID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    public List<Event> getAllEvents() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Event", e);
        }
    }

    public Event getEventByEventID(String eventID) {
        try {
            return repository.getEventByEventID(eventID);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching the ticket", e);
        }
    }

    public String addEvent(Event event) {
        try {
            event.setEventID(UUID.randomUUID().toString().split("-")[0]);
            repository.save(event);
            return "Event Creating Successful";
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Creating Event", e);
        }
    }

    //TODO: Error
    public String deleteEventByEventID(String eventID) {
        try {
            if (repository.existsByEventID(eventID)) {
                repository.deleteById(eventID);
                return eventID + " Deleted Successfully";
            } else {
                return eventID + " Event doesn't exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Deleting Event", e);
        }
    }

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
}