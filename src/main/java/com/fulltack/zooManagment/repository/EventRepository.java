package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


    public interface EventRepository extends MongoRepository<Event, String> {

        //@Query("{EventID:  ?0}")
        //Event getEventByEventID(String EventID);
    }


