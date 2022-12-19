package com.lisitsin.simpleRestWithSpring.repository;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
