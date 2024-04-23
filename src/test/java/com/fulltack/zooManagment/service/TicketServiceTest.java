package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.TicketRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket1, ticket2;

    @BeforeEach
    void setUp() {
        ticket1 = new Ticket();
        ticket1.setTicketID("123");
        ticket1.setUsername("JohnDoe");

        ticket2 = new Ticket();
        ticket2.setTicketID("456");
        ticket2.setUsername("JaneDoe");

        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));
    }

    @Test
    void getAllTickets_ShouldReturnAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        assertTrue(tickets.contains(ticket1));
        assertTrue(tickets.contains(ticket2));
    }

    @Test
    void getAllTickets_ShouldHandleExceptions() {
        when(ticketRepository.findAll()).thenThrow(new RuntimeException("Database is down"));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            ticketService.getAllTickets();
        });

        assertEquals("Error Occurred while fetching all Tickets", exception.getMessage());
    }

//    @Test
//    void addTicket_ShouldCreateTicketSuccessfully() {
//        TicketRequest ticketRequest = new TicketRequest();
//        ticketRequest.setTicketType(TicketType.FOREIGN_ADULT);
////        ticketRequest.setStatus(TicketStatus.VALID);
//        ticketRequest.setPrice(25.0);
//        ticketRequest.setTicketDate(LocalDate.now());
//        ticketRequest.setUsername("JohnDoe");
//
//        Ticket convertedTicket = new Ticket();
//        convertedTicket.setTicketType(TicketType.FOREIGN_ADULT);
//        convertedTicket.setStatus(TicketStatus.VALID);
//        convertedTicket.setPrice(25.0);
//        convertedTicket.setTicketDate(LocalDate.now());
//        convertedTicket.setUsername("JohnDoe");
//
//        when(ticketService.convertToTicket(ticketRequest)).thenReturn(convertedTicket);
//        when(ticketRepository.save(any(Ticket.class))).thenReturn(convertedTicket);
//
//        String result = ticketService.addTicket(ticketRequest);
//        assertEquals("Ticket Created Successful", result);
//    }


//    @Test
//    void addTicket_ShouldFailWhenTicketInfoIsInvalid() {
//        TicketRequest ticketRequest = new TicketRequest();
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            ticketService.addTicket(ticketRequest);
//        });
//
//        assertEquals("Ticket type and status must be valid.", exception.getMessage());
//    }

//    @Test
//    void deleteTicketByTicketID_ShouldDeleteSuccessfully() {
//        String ticketID = "123";
//        when(ticketRepository.existsById(ticketID)).thenReturn(true);
//
//        String result = ticketService.deleteTicketByTicketID(ticketID);
//        assertEquals("Deleted Successfully", result);
//        verify(ticketRepository, times(1)).deleteById(ticketID);
//    }

//    @Test
//    void deleteTicketByTicketID_ShouldReturnErrorMessageIfTicketDoesNotExist() {
//        String ticketID = "123";
//        when(ticketRepository.existsById(ticketID)).thenReturn(false);
//
//        String result = ticketService.deleteTicketByTicketID(ticketID);
//        assertEquals("Ticket doesn't exists", result);
//        verify(ticketRepository, never()).deleteById(ticketID);
//    }

//    @Test
//    void getTicketByTicketID_ShouldReturnTicketIfFound() {
//        String ticketID = "123";
//        Ticket ticket = new Ticket();
//        ticket.setTicketID(ticketID);
//        when(ticketRepository.getTicketByTicketID(ticketID)).thenReturn(ticket);
//
//        Ticket result = ticketService.getTicketByTicketID(ticketID);
//        assertNotNull(result);
//        assertEquals(ticketID, result.getTicketID());
//    }

//    @Test
//    void getTicketByTicketID_ShouldThrowExceptionIfNotFound() {
//        String ticketID = "unknown";
//        when(ticketRepository.getTicketByTicketID(ticketID)).thenReturn(null);
//
//        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.getTicketByTicketID(ticketID);
//        });
//
//        assertEquals("Ticket with ID " + ticketID + " not found", exception.getMessage());
//    }

}