package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event")
public class Event {

    @Id
    private String id;
    private String eventID;
    private String eventName;
    private String eventDescription;
    private LocalDateTime eventDate;
    private String eventLocation;
    private int capacity;
    private String eventManager;
    private String username;
}