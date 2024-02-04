package com.fulltack.zooManagment.service;



import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private TicketRepository repository;
    public List<Ticket> getAllTickets(){
        return repository.findAll();}

    public Ticket addTask(Ticket ticket){
        ticket.setTicketID(UUID.randomUUID().toString().split("-")[0]);
        return  repository.save(ticket);
    }

}
