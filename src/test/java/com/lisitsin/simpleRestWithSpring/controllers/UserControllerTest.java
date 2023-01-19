package com.lisitsin.simpleRestWithSpring.controllers;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    private Long id;

    private String token;

    @Before
    public void createContext() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "admin");

        Gson gson = new Gson();
        String json = gson.toJson(map);

        MvcResult admin = this.mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        String contentAsString1 = admin.getResponse().getContentAsString();
        int l = contentAsString1.lastIndexOf(":");
        int k = contentAsString1.lastIndexOf("\"");
        token = contentAsString1.substring(l+2,k);

        UserEntity user = new UserEntity();
        user.setUsername("blabla");
        user.setPassword("blabla");
        UserEntity register = userService.register(user);
        id = register.getId();
    }

    @Test
    public void getUserWithRoleAdmin() throws Exception{
        this.mockMvc.perform(get("/api/v1/users/2")
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("andrey"))).andReturn();
    }

    @Test
    public void updateUser() throws Exception{

        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername("ROman");
        user.setPassword("sdffdsf");
        user.setStatus(Status.ACTIVE);

        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mockMvc.perform(put("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer_"+ token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ROman")));

        Assert.assertEquals(user.getId(), id);
    }

    @After
    public void deleteUser() throws Exception{
        this.mockMvc.perform(delete("/api/v1/users/"+id)
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
