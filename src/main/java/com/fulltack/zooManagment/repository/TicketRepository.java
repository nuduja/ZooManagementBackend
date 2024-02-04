package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    @Query("{TicketID:  ?0}")
    Ticket getTicketByTicketID(String TicketID);

    boolean existsByTicketID(String TicketID);
}