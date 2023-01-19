package com.lisitsin.simpleRestWithSpring.service.impl;

import com.lisitsin.simpleRestWithSpring.model.Role;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.repository.RoleRepository;
import com.lisitsin.simpleRestWithSpring.repository.UserRepository;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserEntity register(UserEntity user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        UserEntity registeredUser = userRepository.save(user);
        return registeredUser;
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()){
            Role roleUser = roleRepository.findByName("ROLE_USER");
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);
            user.setRoles(userRoles);
        }
        UserEntity updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public UserEntity findById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
