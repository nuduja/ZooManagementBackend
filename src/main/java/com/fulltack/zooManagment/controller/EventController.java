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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> eventReport() {
        ByteArrayInputStream bis = service.generateEventsPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=events.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @PutMapping("/updatebyeventid/{eventID}")
    public String updateEventByEventId(@PathVariable String eventID, @RequestBody Map<String, Object> updates) {
        service.updateEventByEventId(eventID, updates);
        return "Event updated successfully";
    }

}