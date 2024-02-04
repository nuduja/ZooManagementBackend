package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "ticket")
public class Ticket {
    @Id
    private String id;
    private String TicketID;
    private String TicketType;
    private double Price;
    private String Availability;
}

