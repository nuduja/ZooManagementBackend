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
//    @RequestMapping(value="/")
//    public void redirect(HttpServletResponse response) throws IOException{
//        response.sendRedirect("/swagger-ui.html");
//    }

//    @Autowired
//    TicketRepository repo;

    @Autowired
    private TicketService service;

    //    @GetMapping("/tickets")
    @GetMapping
    public List<Ticket> getAllTickets(){
        return service.getAllTickets();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket addTicket(@RequestBody Ticket ticket){
        return service.addTask(ticket);
    }
}