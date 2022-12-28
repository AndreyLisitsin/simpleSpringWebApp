package com.lisitsin.simpleRestWithSpring.service;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void save(MultipartFile file);

    ResponseEntity<InputStreamResource> loadFile(String filename);

    void deleteById(Long id);
}
