package com.lisitsin.simpleRestWithSpring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {

    private Long id;
    private String name;
    private String filePath;

    public  FileDto(FileEntity fileEntity){
        this.name = fileEntity.getName();
        this.filePath = fileEntity.getFilePath();
        this.id = fileEntity.getId();
    }
}
