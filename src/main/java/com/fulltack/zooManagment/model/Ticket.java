package com.fulltack.zooManagment.model;

import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ticket")
public class Ticket {
    @Id
    private String id;
    @Indexed(unique = true)
    private String ticketID;
    private TicketType ticketType;
    private double price;
    private TicketStatus status;
    private LocalDate ticketDate;
    private String username;
}