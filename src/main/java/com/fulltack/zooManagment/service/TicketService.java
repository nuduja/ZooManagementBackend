package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
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
        try{
            return repository.findAll();
        } catch(Exception e){
            throw new ServiceException("Error Occurred while fetching all Tickets", e);
        }
    }

    public Ticket getTicketByTicketID(String ticketID){
        try{
            return repository.getTicketByTicketID(ticketID);
        } catch(Exception e){
            throw new ServiceException("Error occurred while fetching the ticket", e);
        }
    }

    public String addTask(Ticket ticket){
        try{
            ticket.setTicketID(UUID.randomUUID().toString().split("-")[0]);
            repository.save(ticket);
            return "Ticket Creating Successful";
        } catch (Exception e){
            throw new ServiceException("Error Occurred while Creating Ticket", e);
        }
    }

    public String deleteTicketByTicketID(String ticketID){
        try {
            if(repository.existsByTicketID(ticketID)){
                repository.deleteById(ticketID);
                return ticketID + " Deleted Successfully";
            }
            else{
                return ticketID + " Ticket doesn't exists";
            }
        } catch(Exception e){
            throw new ServiceException("Error Deleting Ticket", e);
        }
    }

    public String updateTicket(Ticket ticketRequest){
        try{
            if(repository.existsByTicketID(ticketRequest.getTicketID())){
                Ticket existingTicket = repository.findById(ticketRequest.getId()).get();
                existingTicket.setTicketType(ticketRequest.getTicketType());
                existingTicket.setPrice(ticketRequest.getPrice());
                existingTicket.setAvailability(ticketRequest.getAvailability());

                return ticketRequest.getTicketID() + " Ticket Creating Successful";
            }
            else{
                return ticketRequest.getTicketID() + " Ticket Creating Unsuccessful";
            }
        } catch(Exception e){
            throw new ServiceException("Error Updating Ticket", e);
        }
    }
}