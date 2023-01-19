package com.lisitsin.simpleRestWithSpring.controller;


import com.lisitsin.simpleRestWithSpring.dto.UserDto;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/registration")
public class RegistrationRestControllerV1 {

    private final UserService userService;

    @Autowired
    public RegistrationRestControllerV1(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDto> registrationUser(@RequestBody UserEntity user){

        if (user.getUsername()!= null && user.getPassword() != null) {

            UserEntity registeredUser = userService.register(user);

            return new ResponseEntity<>(new UserDto(registeredUser), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
