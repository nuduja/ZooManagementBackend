package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findByUsername(String username);

    @Query("{'TicketID':  ?0}")
    Ticket getTicketByTicketID(String TicketID);

    boolean existsByTicketID(String TicketID);
}