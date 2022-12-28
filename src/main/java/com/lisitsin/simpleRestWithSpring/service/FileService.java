package com.lisitsin.simpleRestWithSpring.service;

import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.Valid;
import java.util.List;

public interface FileService {
    FileEntity save(FileEntity file, String filename);

    FileEntity update(FileEntity file);

    List<FileEntity> getAll();

    FileEntity findById(Long id);

    void deleteById(Long id);
}
