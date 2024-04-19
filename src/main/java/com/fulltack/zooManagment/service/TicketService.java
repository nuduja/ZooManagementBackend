package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.TicketRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    public Ticket convertToTicket(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setTicketID(UUID.randomUUID().toString().split("-")[0]);
        ticket.setTicketType(ticketRequest.getTicketType());
        ticket.setStatus(TicketStatus.VALID);
        ticket.setPrice(ticketRequest.getPrice());
        ticket.setTicketDate(ticketRequest.getTicketDate());
        ticket.setUsername(ticketRequest.getUsername());
        return ticket;
    }

    public List<Ticket> getAllTickets(){
        try{
            return repository.findAll();
        } catch(Exception e){
            throw new ServiceException("Error Occurred while fetching all Tickets", e);
        }
    }

    public Ticket getTicketByTicketID(String ticketID){
        try{
            Ticket ticket = repository.getTicketByTicketID(ticketID);

            if (ticket == null) {
                throw new TicketNotFoundException("Ticket with ID " + ticketID + " not found");
            }
            return ticket;
        } catch(Exception e){
            throw new ServiceException("Error occurred while fetching the ticket", e);
        }
    }

    public String addTicket(TicketRequest ticketRequest){
        try{
            Ticket ticket = convertToTicket(ticketRequest);
            if (ticket.getTicketType() == null || ticket.getStatus() == null) {
                throw new IllegalArgumentException("Ticket type and status must be valid.");
            }
            repository.save(ticket);
            return "Ticket Created Successful";
        } catch (Exception e){
            throw new ServiceException("Error Occurred while Creating Ticket", e);
        }
    }

    public String deleteTicketByTicketID(String TicketID){
        try {
            if(repository.existsById(TicketID)){
                repository.deleteById(TicketID);
                return "Deleted Successfully";
            }
            else{
                return "Ticket doesn't exists";
            }
        } catch(Exception e){
            throw new ServiceException("Error Deleting Ticket", e);
        }
    }

    //TODO: DO
    public String updateTicket(Ticket ticket){
        try{
            if(repository.existsByTicketID(ticket.getTicketID())){
                Ticket existingTicket = repository.findById(ticket.getId()).get();
                existingTicket.setTicketType(ticket.getTicketType());
                existingTicket.setPrice(ticket.getPrice());

                return ticket.getTicketID() + " Ticket Updated Successful";
            }
            else{
                return ticket.getTicketID() + " Ticket Updating Unsuccessful";
            }
        } catch(Exception e){
            throw new ServiceException("Error Updating Ticket", e);
        }
    }

    public List<Ticket> searchTickets(String ticketID, TicketType ticketType, TicketStatus status) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (ticketID != null && !ticketID.isEmpty()) {
                criteria.add(Criteria.where("ticketID").regex(ticketID, "i"));
            }
            if (ticketType != null) {
                criteria.add(Criteria.where("ticketType").is(ticketType));
            }
            if (status != null) {
                criteria.add(Criteria.where("status").is(status));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, Ticket.class);
        } catch(Exception e){
            throw new ServiceException("Error Searching Ticket", e);
        }
    }

    public List<Ticket> getTicketsByUsername(String username) {
        try {
            return repository.findByUsername(username);
        } catch(Exception e){
            throw new ServiceException("Error fetching Ticket", e);
        }
    }
    public void updateTicketByTicketId(String ticketID, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("ticketID").is(ticketID));
        Update update = new Update();
        updates.forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("ticketID")) {
                update.set(key, value);
            }
        });
        mongoTemplate.findAndModify(query, update, Ticket.class);
    }

    public ByteArrayInputStream generateTicketsPDF() {
        List<Ticket> tickets = repository.findAll();
        return pdfGeneratorService.ticketReport(tickets);
    }

    public Map<String, Object> getTicketStatistics() {
        List<Ticket> allTickets = repository.findAll();
        Map<TicketType, Long> countByType = allTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getTicketType, Collectors.counting()));
        Map<TicketStatus, Long> countByStatus = allTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getStatus, Collectors.counting()));
        double totalIncome = allTickets.stream()
                .mapToDouble(Ticket::getPrice).sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("Total Tickets", allTickets.size());
        stats.put("Count By Type", countByType);
        stats.put("Count By Status", countByStatus);
        stats.put("Total Income", totalIncome);

        return stats;
    }

}