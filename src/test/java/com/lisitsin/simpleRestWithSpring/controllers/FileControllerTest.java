package com.lisitsin.simpleRestWithSpring.controllers;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.dto.FileDto;
import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;
    @Autowired
    private FileService fileService;

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
    public void getFileByIdSuccess() throws Exception{

        FileEntity file = fileService.findById(38L);

        Gson gson = new Gson();
        String fileFromBD = gson.toJson(file);

        this.mockMvc.perform(get("/api/v1/files/38")
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(fileFromBD));
    }
    @Test
    public void getFileByIdNoContent() throws Exception{
        this.mockMvc.perform(get("/api/v1/files/39")
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getLIstOfFilesSuccess() throws Exception{
        List<FileDto> fileDtos = fileService.getAll().stream().map(FileDto::new).collect(Collectors.toList());

        Gson gson = new Gson();
        String filesFromBD = gson.toJson(fileDtos);

        this.mockMvc.perform(get("/api/v1/files/")
                        .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(filesFromBD));
    }

    @Test
    public void saveFile() throws Exception{

        byte[] array = Files.readAllBytes(Paths.get("src/test/resources/test.txt"));
        MockPart filepart = new MockPart("file", "test.txt", array);
        filepart.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        String f ="{\n" +
                "  \"event\": {\n" +
                "    \"id\": 10,\n" +
                "    \"name\": \"learning\"\n" +
                "  }\n" +
                "}";

        byte[] json = f.getBytes(StandardCharsets.UTF_8);
        MockPart jsonPart = new MockPart("fileEntity", "test.txt", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(multipart("/api/v1/files/").part(filepart).part(jsonPart)
                .header("Authorization", "Bearer_"+ token))
                .andDo(print())
                .andExpect(status().isCreated());
    }


}
