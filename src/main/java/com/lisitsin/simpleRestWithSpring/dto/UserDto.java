package com.lisitsin.simpleRestWithSpring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private Long id;
    private String username;
    private List<EventDto> events;

    public  UserDto(UserEntity userEntity){
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        if (userEntity.getEvents()!= null) {
            this.events = userEntity.getEvents().stream().map(EventDto::new).collect(Collectors.toList());
        }
        else
            this.events = new ArrayList<>();
    }
}
