package com.lisitsin.simpleRestWithSpring.controller;


import com.lisitsin.simpleRestWithSpring.dto.UserDto;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api/v1/admin/")
@Slf4j
public class AdminRestControllerV1 {

    private final UserService userService;

    @PostConstruct
    void init(){
        log.info("Admin controller successfully loaded");
    }

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id")Long id){
        UserEntity user = userService.findById(id);


        if (user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto userDto = new UserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
