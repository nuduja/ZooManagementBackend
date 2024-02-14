package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    public List<Event> getAllEvents(){return repository.findAll();}

    public Event getEventByEventID(String eventID){return repository.getEventByEventID(eventID);}

    public Event addEvent(Event event){
        event.setEventID((UUID.randomUUID().toString().split("-")[0]));
        return repository.save(event);
    }

    //TODO: Error
    public String deleteEvent(String id){
        repository.deleteById(id);
        return id + " Deleted Successfully";
    }

    public Event updateEvent(Event eventRequest){
        Event existingEvent = repository.findById(eventRequest.getId()).get();
        existingEvent.setEventName(eventRequest.getEventName());
        existingEvent.setEventDescription(eventRequest.getEventDescription());
        existingEvent.setEventLocation(eventRequest.getEventLocation());
        existingEvent.setCapacity(eventRequest.getCapacity());

        return repository.save(existingEvent);
    }



}