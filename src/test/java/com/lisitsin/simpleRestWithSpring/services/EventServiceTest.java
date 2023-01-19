package com.lisitsin.simpleRestWithSpring.services;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.repository.EventRepository;
import com.lisitsin.simpleRestWithSpring.service.impl.EventServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private List<EventEntity> events;


    @Before
    public void init(){
        eventRepository = Mockito.mock(EventRepository.class);
        eventService = new EventServiceImpl(eventRepository);

        events = new ArrayList<>();
        EventEntity eventAfterSave = new EventEntity();
        eventAfterSave.setId(1L);
        eventAfterSave.setName("marring");
        eventAfterSave.setUser(new UserEntity());


        EventEntity secondEvent = new EventEntity();
        secondEvent.setId(2L);
        secondEvent.setName("running");
        secondEvent.setUser(new UserEntity());

        events.add(eventAfterSave);
        events.add(secondEvent);
    }

    @Test
    public void saveEvent(){
        EventEntity eventBeforeSave = new EventEntity();
        eventBeforeSave.setName("marring");
        eventBeforeSave.setUser(new UserEntity());

        Mockito.when(eventRepository.save(eventBeforeSave)).thenReturn(events.get(0));
        EventEntity eventFromDb = eventService.save(eventBeforeSave);
        Assert.assertEquals(events.get(0), eventFromDb);
    }

    @Test
    public void findAllEvents(){
        Mockito.when(eventRepository.findAll()).thenReturn(events);
        List<EventEntity> eventsFromDb = eventService.getAll();
        Assert.assertEquals(events, eventsFromDb);
        System.out.println(events);
        Assertions.assertThat(eventsFromDb).isNotNull().isNotEmpty();
    }

    @Test
    public void findEventById(){
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(events.get(0)));
        EventEntity eventFromDb = eventService.findById(1L);
        Assert.assertEquals(events.get(0), eventFromDb);
    }

    @Test
    public void updateEvent(){
        EventEntity eventBeforeUpdate = events.get(0);
        EventEntity eventAfterUpdate = events.get(0);
        eventAfterUpdate.setName("learning");

        Mockito.when(eventRepository.save(eventBeforeUpdate)).thenReturn(eventAfterUpdate);
        EventEntity eventFromDb = eventService.update(eventBeforeUpdate);
        Assert.assertEquals(eventAfterUpdate, eventFromDb);
    }
}
