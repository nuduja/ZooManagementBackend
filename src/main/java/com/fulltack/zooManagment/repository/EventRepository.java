package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface EventRepository extends MongoRepository<Event, String> {

        @Query("{EventID:  ?0}")
        Event getEventByEventID(String EventID);
    }


