package com.lisitsin.simpleRestWithSpring.service;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;

import java.util.List;

public interface EventService {
    EventEntity register(EventEntity user);

    EventEntity update(EventEntity user);

    List<EventEntity> getAll();

    EventEntity findById(Long id);

    void delete(Long id);
}
