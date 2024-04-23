package com.fulltack.zooManagment.Requests;

import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.validators.ValidTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotNull
    private TicketType ticketType;

    private double price;

    @NotNull
    private String username;

    @NotNull
    private LocalDate ticketDate;

    public @NotNull String getUsername() {
        return username;
    }
}
