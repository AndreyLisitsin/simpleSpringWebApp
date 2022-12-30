package com.lisitsin.simpleRestWithSpring;

import com.lisitsin.simpleRestWithSpring.model.Role;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.repository.RoleRepository;
import com.lisitsin.simpleRestWithSpring.repository.UserRepository;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleRestWithSpringApplicationTests {

	@Autowired
	private UserService userService;
	@MockBean
	private  UserRepository userRepository;
	@MockBean
	private RoleRepository roleRepository;

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Before
	public void setUp(){
		UserEntity user = new UserEntity();
		user.setId(1L);
		user.setUsername("Mike");
		Role role = new Role();
		user.setRoles(List.of(role));
		user.setPassword("$2a$04$xtoWtz7UtuXTkJ1kmAkHAO5rNhXJhjWSYhpK584eWli8VomIg.1zG");
		user.setRoles(List.of());
		Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		System.out.println("Inicialization complete");
	}


	@Test
	public void getUserById() {
		UserEntity user = userService.findById(1L);
		Assert.assertEquals(user.getUsername(), "Mike");
		Assert.assertEquals(user.getPassword(), "admin");
	}



}

