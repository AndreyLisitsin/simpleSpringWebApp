package com.lisitsin.simpleRestWithSpring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private String username;
    private List<EventDto> events;

    public  UserDto(UserEntity userEntity){
        List<EventDto> eventDtoList = userEntity.getEvents().stream().map(EventDto::new).collect(Collectors.toList());
        this.username = userEntity.getUsername();
        this.events = eventDtoList;

    }
}
