package com.lisitsin.simpleRestWithSpring.controller;

import com.google.gson.Gson;
import com.lisitsin.simpleRestWithSpring.dto.FileDto;
import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import com.lisitsin.simpleRestWithSpring.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/files/")
@Slf4j
public class FileRestControllerV1 {
    private final FileService fileService;
    private final StorageService storageService;

    @Autowired
    public FileRestControllerV1(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable(value = "id")Long id){

        FileEntity file = fileService.findById(id);
        if (file == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        FileDto fileDto = new FileDto(file);
        return new ResponseEntity<>(fileDto, HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping
    public ResponseEntity<List<FileDto>> getListOfUsers(){
        List<FileEntity> files = fileService.getAll();
        List<FileDto> fileDtoList = files.stream().map(FileDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(fileDtoList, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileDto> uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                              @RequestPart("fileEntity") String fileEntityJson){

        FileEntity fileEntity = new Gson().fromJson(fileEntityJson, FileEntity.class);

        try {
            fileService.save(fileEntity , multipartFile.getOriginalFilename());
            storageService.save(multipartFile);
        } catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        FileDto fileDto = new FileDto(fileEntity);

        return new ResponseEntity<>(fileDto, HttpStatus.CREATED);

    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping
    public ResponseEntity<FileDto> updateFile(@RequestBody FileEntity fileEntity){

        try {
            fileService.update(fileEntity);
        } catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        FileDto fileDto = new FileDto(fileEntity);
        return new ResponseEntity<>(fileDto, HttpStatus.ACCEPTED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping(value = "{id}")
    public ResponseEntity<FileDto> deleteFile(@PathVariable(value = "id")Long id){

        try {
            storageService.deleteById(id);
            fileService.deleteById(id);
        } catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>( HttpStatus.OK);

    }


}
