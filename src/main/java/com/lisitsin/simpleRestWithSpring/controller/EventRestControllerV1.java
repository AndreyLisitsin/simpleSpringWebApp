package com.lisitsin.simpleRestWithSpring.controller;

import com.lisitsin.simpleRestWithSpring.dto.EventDto;
import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import com.lisitsin.simpleRestWithSpring.security.jwt.JwtTokenProvider;
import com.lisitsin.simpleRestWithSpring.service.EventService;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/events/")
public class EventRestControllerV1 {
    private final EventService eventService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EventRestControllerV1(EventService eventService,UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.eventService = eventService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<EventDto> saveEvent(@RequestBody EventEntity event,
                                              @RequestHeader(value = "Authorization")String token){
        Long userId = jwtTokenProvider.getUserId(token);
        event.setUser(userService.findById(userId));
        eventService.save(event);
        return new ResponseEntity<>(new EventDto(event), HttpStatus.CREATED);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<EventDto> findById(@PathVariable(value = "id")Long id){
        EventEntity event = eventService.findById(id);
        return new ResponseEntity<>(new EventDto(event), HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping
    public ResponseEntity<List<EventDto>> findAll(){
        List<EventEntity> all = eventService.getAll();
        List<EventDto> eventDtos = all.stream().map(EventDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventEntity event,
                                                @RequestHeader(value = "Authorization")String token ){

        if (event.getUser() == null) {
            Long userId = jwtTokenProvider.getUserId(token);
            event.setUser(userService.findById(userId));
        }
        EventEntity update = eventService.update(event);
        return new ResponseEntity<>(new EventDto(update), HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteEvent(@PathVariable(value = "id")Long id){
        eventService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
