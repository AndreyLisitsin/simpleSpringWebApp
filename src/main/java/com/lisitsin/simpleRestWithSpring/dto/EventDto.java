package com.lisitsin.simpleRestWithSpring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private Long id;
    private String name;
    private FileDto file;

    public EventDto(EventEntity eventEntity){
        if (eventEntity.getFile() != null) {
            this.file = new FileDto(eventEntity.getFile());
        }
        this.id = eventEntity.getId();
        this.name = eventEntity.getName();
    }
}
