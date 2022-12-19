package com.lisitsin.simpleRestWithSpring.service;

import com.lisitsin.simpleRestWithSpring.model.FileEntity;

import java.util.List;

public interface FileService {
    FileEntity register(FileEntity user);

    FileEntity update(FileEntity user);

    List<FileEntity> getAll();

    FileEntity findById(Long id);

    void delete(Long id);
}
