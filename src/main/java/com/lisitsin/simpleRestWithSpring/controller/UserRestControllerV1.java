package com.lisitsin.simpleRestWithSpring.controller;

import com.lisitsin.simpleRestWithSpring.dto.UserDto;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.security.jwt.JwtTokenProvider;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users/")
@Slf4j
public class UserRestControllerV1 {
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value ="{id}" )
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id")Long id,
                                               @RequestHeader("Authorization")String token) {
        // читаемость?
        if (!jwtTokenProvider.getUserId(token).equals(id) && jwtTokenProvider.getUsersAuthorities(token).size() == 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserEntity user = userService.findById(id);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto userDto = new UserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping
    public ResponseEntity<List<UserDto>> getListOfUsers(){
        List<UserEntity> users = userService.getAll();
        List<UserDto> userDtoList = users.stream().map(UserDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @Secured(value = "ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserEntity user){
        UserEntity userAfterUpdate = userService.update(user);
        return new ResponseEntity<>(new UserDto(userAfterUpdate), HttpStatus.OK);
    }

    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
