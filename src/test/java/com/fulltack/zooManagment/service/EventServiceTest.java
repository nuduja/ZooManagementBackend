package com.fulltack.zooManagment.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fulltack.zooManagment.Requests.EventRequest;
import com.fulltack.zooManagment.enums.EventStatus;
import com.fulltack.zooManagment.exception.EventNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import com.fulltack.zooManagment.model.Event;
import com.fulltack.zooManagment.repository.EventRepository;
import com.jayway.jsonpath.Criteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.management.Query;
import java.io.ByteArrayInputStream;
import java.util.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void convertToEventTest() {
//        EventRequest request = new EventRequest();
//        request.setEventName("Zoo Visit");
//        request.setEventDescription("Visit the Zoo");
//        request.setEventLocation("City Zoo");
//        request.setUsername("user1");
//
//        Event event = eventService.convertToEvent(request);
//
//        assertNotNull(event.getEventID());
//        assertEquals("Zoo Visit", event.getEventName());
//        assertEquals("Visit the Zoo", event.getEventDescription());
//        assertEquals("City Zoo", event.getEventLocation());
//        assertEquals("user1", event.getUsername());
//    }

    @Test
    void getAllEventsTest() {
        Event event = new Event();
        when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));

        List<Event> result = eventService.getAllEvents();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(eventRepository, times(1)).findAll();
    }

//    @Test
//    void addEventTest() {
//        EventRequest request = new EventRequest();
//        request.setEventName("Zoo Visit");
//        request.setEventDescription("Visit the Zoo");
//
//        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        String result = eventService.addEvent(request);
//
//        assertEquals("Event Creating Successful", result);
//    }

    @Test
    void deleteEventByEventIDTest() {
        String eventID = UUID.randomUUID().toString();
        when(eventRepository.existsByEventID(eventID)).thenReturn(true);

        String result = eventService.deleteEventByEventID(eventID);

        assertEquals("Event Deleted Successfully", result);
        verify(eventRepository).deleteByEventID(eventID);
    }

    @Test
    void getEventByEventIDTest() {
        String eventID = UUID.randomUUID().toString();
        Event event = new Event();
        when(eventRepository.getEventByEventID(eventID)).thenReturn(event);

        Event result = eventService.getEventByEventID(eventID);

        assertNotNull(result);
        verify(eventRepository).getEventByEventID(eventID);
    }

//    @Test
//    void getEventByEventIDNotFoundTest() {
//        String eventID = UUID.randomUUID().toString();
//        when(eventRepository.getEventByEventID(eventID)).thenReturn(null);
//
//        assertThrows(EventNotFoundException.class, () -> eventService.getEventByEventID(eventID));
//    }

//    @Test
//    void searchEventsTest() {
//        Event event = new Event();
//        List<Event> events = Collections.singletonList(event);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("eventID").is("123"));
//        when(mongoTemplate.find(query, Event.class)).thenReturn(events);
//
//        List<Event> result = eventService.searchEvents("123", null, null);
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//    }

    @Test
    void getEventsByUsernameTest() {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventRepository.findByUsername("user1")).thenReturn(events);

        List<Event> result = eventService.getEventsByUsername("user1");

        assertEquals(2, result.size());
    }

    @Test
    void updateEventManagerTest() {
        String eventID = UUID.randomUUID().toString();
        Event event = new Event();
        event.setEventManager("Manager1");
        when(eventRepository.findByEventID(eventID)).thenReturn(Optional.of(event));

        eventService.updateEventManager(eventID, "Manager2");

        assertEquals("Manager2", event.getEventManager());
        verify(eventRepository).save(event);
    }

    @Test
    void generateEventsPDFTest() {
        Event event = new Event();
        List<Event> events = Collections.singletonList(event);
        ByteArrayInputStream stream = new ByteArrayInputStream(new byte[0]);
        when(eventRepository.findAll()).thenReturn(events);
        when(pdfGeneratorService.eventReport(events)).thenReturn(stream);

        ByteArrayInputStream result = eventService.generateEventsPDF();

        assertNotNull(result);
    }

//    @Test
//    void updateEventByEventIdTest() {
//        String eventID = UUID.randomUUID().toString();
//        Event event = new Event();
//        Query query = new Query(Criteria.where("eventID").is(eventID));
//        when(mongoTemplate.findAndModify(eq(query), any(), eq(Event.class))).thenReturn(event);
//
//        eventService.updateEventByEventId(eventID, Collections.singletonMap("eventDescription", "Updated Description"));
//
//        verify(mongoTemplate).findAndModify(eq(query), any(), eq(Event.class));
//    }
}
