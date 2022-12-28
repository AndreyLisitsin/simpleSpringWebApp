package com.lisitsin.simpleRestWithSpring.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import com.lisitsin.simpleRestWithSpring.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/download/")
@Slf4j
public class DownloadFileRestControllerV1 {
    private final StorageService storageService;

    @Autowired
    public DownloadFileRestControllerV1(StorageService storageService){
        this.storageService = storageService;
    }

    @GetMapping(value = "{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable(value = "fileName")String filename)  throws Exception{
            return storageService.loadFile(filename);
    }
}
