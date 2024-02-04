package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}
