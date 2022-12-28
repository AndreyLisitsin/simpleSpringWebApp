package com.lisitsin.simpleRestWithSpring.service;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;

import java.util.List;

public interface EventService {
    EventEntity save(EventEntity event);

    EventEntity update(EventEntity event);

    List<EventEntity> getAll();

    EventEntity findById(Long id);

    void delete(Long id);
}
