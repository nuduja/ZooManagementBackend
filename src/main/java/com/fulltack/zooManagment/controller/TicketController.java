package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.TicketRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.List;

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
}