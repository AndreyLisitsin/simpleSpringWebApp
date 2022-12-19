package com.lisitsin.simpleRestWithSpring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private FileDto file;

    public EventDto(EventEntity eventEntity){
        this.file = new FileDto(eventEntity.getFile());
    }
}
