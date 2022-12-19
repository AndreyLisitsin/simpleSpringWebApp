package com.lisitsin.simpleRestWithSpring.service.impl;

import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.repository.FileRepository;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Override
    public FileEntity register(FileEntity file) {
        file.setStatus(Status.ACTIVE);
        fileRepository.save(file);
        return file;
    }

    @Override
    public FileEntity update(FileEntity file) {
        return null;
    }

    @Override
    public List<FileEntity> getAll() {
        return null;
    }

    @Override
    public FileEntity findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
