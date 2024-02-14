package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{eventID}")
    public Event getEvent(@PathVariable String eventID){
        return service.getEventByEventID(eventID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addEvent(@RequestBody Event event) {
        return service.addEvent(event);
    }

    @DeleteMapping("/{eventID}")
    public String deleteEvent(@PathVariable String eventID){
        return service.deleteEventByEventID(eventID);
    }

    @PutMapping
    public String updateEvent(@RequestBody Event event){
        return service.updateEvent(event);
    }
}

