package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByEventID(String eventID);

    @Query("{ 'eventID': ?0 }")
    void updateEventManagerByEventID(String eventID, String eventManager);

    List<Event> findByUsername(String username);

    @Query("{EventID:  ?0}")
    Event getEventByEventID(String EventID);

    boolean existsByEventID(String eventID);

    String deleteByEventID(String eventID);
}


