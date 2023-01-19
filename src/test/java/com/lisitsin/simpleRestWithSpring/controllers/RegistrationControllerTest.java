package com.lisitsin.simpleRestWithSpring.controllers;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.controller.RegistrationRestControllerV1;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = "/create-user-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegistrationRestControllerV1 registrationRestControllerV1;

    @Test
    public void RegisterUserSuccess() throws Exception{
        UserEntity user = new UserEntity();
        user.setUsername("andreySecond");
        user.setPassword("andreySecond");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("andreySecond")));
    }

    @Test
    public void RegisterUserDenied() throws Exception{
        UserEntity user = new UserEntity();
        user.setUsername("fgbhfghfgh");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
