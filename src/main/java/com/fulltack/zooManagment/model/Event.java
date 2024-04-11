package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "event")
public class Event {

@Id
private String id;
private String eventID;
private String eventName;
private String eventDescription;
private String eventDate;
private String eventLocation;
private String capacity;
}