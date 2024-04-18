package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.EventManagerUpdateDTO;
import com.fulltack.zooManagment.Requests.EventRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable String eventID){
        try {
            return ResponseEntity.ok(service.getEventByEventID(eventID));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createEvent(@RequestBody EventRequest eventRequest) {
        try {
            return ResponseEntity.ok(service.addEvent(eventRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{eventID}")
    public ResponseEntity<String> deleteEvent(@PathVariable String eventID){
        try {
            return ResponseEntity.ok(service.deleteEventByEventID(eventID));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateEvent(@RequestBody Event event){
        try {
            return ResponseEntity.ok(service.updateEvent(event));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public List<Event> searchEvents(
            @RequestParam(required = false) String eventID,
            @RequestParam(required = false) String eventLocation,
            @RequestParam(required = false) String eventManager) {
        return service.searchEvents(eventID, eventLocation, eventManager);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.getEventsByUsername(username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update-manager")
    public String updateEventManager(@RequestBody EventManagerUpdateDTO updateDTO) {
        service.updateEventManager(updateDTO.getEventID(), updateDTO.getEventManager());
        return "Event manager updated successfully";
    }
}