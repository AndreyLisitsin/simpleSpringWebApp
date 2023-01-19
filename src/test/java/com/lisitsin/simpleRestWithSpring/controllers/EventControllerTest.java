package com.lisitsin.simpleRestWithSpring.controllers;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import com.lisitsin.simpleRestWithSpring.service.EventService;
import org.junit.After;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventService eventService;
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

    }

    @Test
    public void getEvent() throws Exception{
        this.mockMvc.perform(get("/api/v1/events/8")
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":8,\"name\":\"swimmnig\",\"file\":{\"id\":38,\"name\":\"HomeWorkDB.txt\",\"filePath\":\"http://localhost:8075/api/v1/download/HomeWorkDB.txt\"}}"));
    }

    @Test
    public void saveEvent() throws Exception{

        EventEntity event = new EventEntity();
        event.setName("learning");
        Gson gson = new Gson();
        String eventJson = gson.toJson(event);

        EventEntity eventAfterSaving = new EventEntity();
        eventAfterSaving.setName("learning");
        eventAfterSaving.setId(11L);

        String eventAfter = gson.toJson(eventAfterSaving);

        this.mockMvc.perform(post("/api/v1/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson)
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(eventAfter));
    }

    @After
    public void deleteEvent() throws Exception{
        this.mockMvc.perform(delete("/api/v1/events/"+11)
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
