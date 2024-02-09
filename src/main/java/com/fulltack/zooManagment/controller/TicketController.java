package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService service;

    //    @GetMapping("/tickets")
    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    @GetMapping("/{ticketID}")
    public Ticket getTicket(@PathVariable String ticketID) {
        return service.getTicketByTicketID(ticketID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addTicket(@RequestBody Ticket ticket) {
        return service.addTask(ticket);
    }

    @DeleteMapping("/{ticketID}")
    public String deleteTicket(@PathVariable String ticketID) {
        return service.deleteTicketByTicketID(ticketID);
    }

    @PutMapping
    public String updateTicket(@RequestBody Ticket ticket) {
        return service.updateTicket(ticket);
    }
}