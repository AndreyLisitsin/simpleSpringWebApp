package com.lisitsin.simpleRestWithSpring.controllers;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.controller.AuthenticationRequestControllerV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthenticationRequestControllerV1 controllerV1;

	@Test
	public void successfullyAuth() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("username", "admin");
		map.put("password", "admin");

		Gson gson = new Gson();
		String json = gson.toJson(map);

		this.mockMvc.perform(post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("admin")));
	}

	@Test
	public void accessDenied()  throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("username", "admin");
		map.put("password", "123");

		Gson gson = new Gson();
		String json = gson.toJson(map);

		this.mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
}

