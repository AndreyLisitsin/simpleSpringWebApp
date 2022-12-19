package com.lisitsin.simpleRestWithSpring.service;

import com.lisitsin.simpleRestWithSpring.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity register(UserEntity user);

    UserEntity update(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String username);

    UserEntity findById(Long id);

    void delete(Long id);

}
