package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.TicketRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ticket")
@Validated
public class TicketController {

    @Autowired
    private TicketService service;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(service.getAllTickets());
    }

    @GetMapping("/{ticketID}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String ticketID) {
        try {
            return ResponseEntity.ok(service.getTicketByTicketID(ticketID));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        try {
            return ResponseEntity.ok(service.addTicket(ticketRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{TicketID}")
    public ResponseEntity<String> deleteTicket(@PathVariable String TicketID) {
        try {
            return ResponseEntity.ok(service.deleteTicketByTicketID(TicketID));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateTicket(@RequestBody Ticket ticket) {
        try {
            return ResponseEntity.ok(service.updateTicket(ticket));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public List<Ticket> searchTickets(
            @RequestParam(required = false) String ticketID,
            @RequestParam(required = false) TicketType ticketType,
            @RequestParam(required = false) TicketStatus status) {
        return service.searchTickets(ticketID, ticketType, status);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.getTicketsByUsername(username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/updatebyticketid/{ticketID}")
    public String updateEventByEventId(@PathVariable String ticketID, @RequestBody Map<String, Object> updates) {
        service.updateTicketByTicketId(ticketID, updates);
        return "Ticket updated successfully";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> ticketsReport() {
        ByteArrayInputStream bis = service.generateTicketsPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=tickets.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}