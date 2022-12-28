package com.lisitsin.simpleRestWithSpring.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import com.lisitsin.simpleRestWithSpring.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final FileService fileService;
    private final AmazonS3 amazonS3;

    @Value("{aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    public StorageServiceImpl(FileService fileService, AmazonS3 amazonS3) {
        this.fileService = fileService;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void save(MultipartFile multipartFile) {
        try {
            String filename = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            amazonS3.putObject(bucketName, filename, inputStream, new ObjectMetadata());
        } catch (AmazonS3Exception  | IOException exception) {
            log.info(exception.getMessage());
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> loadFile(String filename) {
        try {
            S3Object o = amazonS3.getObject(bucketName, filename);
            S3ObjectInputStream s3is  = o.getObjectContent();
            return ResponseEntity.ok().body(new InputStreamResource(s3is));
        } catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void deleteById(Long id) {
        String filename = fileService.findById(id).getName();
            amazonS3.deleteObject(bucketName, filename);
    }
}
