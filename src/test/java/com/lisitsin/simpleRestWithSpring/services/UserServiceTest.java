package com.lisitsin.simpleRestWithSpring.services;

import com.lisitsin.simpleRestWithSpring.model.Role;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.repository.RoleRepository;
import com.lisitsin.simpleRestWithSpring.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private com.lisitsin.simpleRestWithSpring.service.impl.UserServiceImpl userService;


    @Before
    public void init(){
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        userService = new com.lisitsin.simpleRestWithSpring.service.impl.UserServiceImpl(userRepository, roleRepository, new BCryptPasswordEncoder());
    }

    @Test
    public void registerUserSuccess(){

        UserEntity userBeforeSave = new UserEntity();
        userBeforeSave.setUsername("Andrey");
        userBeforeSave.setPassword("Andrey");

        UserEntity userAfterSave = new UserEntity();
        userAfterSave.setId(1L);
        userAfterSave.setUsername("Andrey");
        userAfterSave.setPassword("Andrey");
        userAfterSave.setStatus(Status.ACTIVE);

        Role role = new Role();
        role.setName("USER_ROLE");
        userAfterSave.setRoles(List.of(role));

        Mockito.when(roleRepository.findByName("USER_ROLE")).thenReturn(role);
        Mockito.when(userRepository.save(userBeforeSave)).thenReturn(userAfterSave);

        UserEntity user = userService.register(userBeforeSave);

        Assert.assertEquals(userBeforeSave.getUsername(),user.getUsername());
        Assert.assertEquals(Long.valueOf(1),user.getId());
    }

    @Test
    public void gatUserById(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Andrey");
        user.setUsername("Andreev");
        user.setStatus(Status.ACTIVE);

        Role role = new Role();
        role.setName("USER_ROLE");
        user.setRoles(List.of(role));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity userFromDb = userService.findById(1L);
        Assert.assertEquals(user, userFromDb);
    }

    @Test
    public void updateUser(){
        UserEntity userBeforeUpdate = new UserEntity();
        userBeforeUpdate.setId(1L);
        userBeforeUpdate.setUsername("Andrey");
        userBeforeUpdate.setUsername("Andreev");
        userBeforeUpdate.setStatus(Status.ACTIVE);

        Role role = new Role();
        role.setName("USER_ROLE");
        userBeforeUpdate.setRoles(List.of(role));

        UserEntity userAfterUpdate = new UserEntity();
        userAfterUpdate.setId(1L);
        userAfterUpdate.setUsername("Roman");
        userAfterUpdate.setUsername("Romanov");
        userAfterUpdate.setStatus(Status.NOT_ACTIVE);
        userAfterUpdate.setRoles(List.of(role));

        Mockito.when(userRepository.save(userBeforeUpdate)).thenReturn(userAfterUpdate);
        UserEntity userFromDb = userService.update(userBeforeUpdate);
        Assert.assertEquals(userAfterUpdate, userFromDb);
    }
}
