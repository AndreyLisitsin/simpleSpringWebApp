package com.lisitsin.simpleRestWithSpring.service.impl;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.repository.EventRepository;
import com.lisitsin.simpleRestWithSpring.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventEntity save(EventEntity event) {
        event.setStatus(Status.ACTIVE);
        eventRepository.save(event);
        return event;
    }

    @Override
    public EventEntity update(EventEntity event) {
        if (event.getStatus() == null){
            event.setStatus(Status.ACTIVE);
        }
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public EventEntity findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
