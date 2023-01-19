package com.lisitsin.simpleRestWithSpring.service.impl;

import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.repository.FileRepository;
import com.lisitsin.simpleRestWithSpring.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${aws.s3.pathToFile}")
    private String fileUrl;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity save(FileEntity file, String fileName) {
        if (file.getName() == null){
            file.setName(fileName);
        }
        if (file.getEvent() == null){
            throw new RuntimeException("File must have an event");
        }
        file.setFilePath(fileUrl + file.getName());
        file.setStatus(Status.ACTIVE);
        fileRepository.save(file);
        return file;
    }

    @Override
    public FileEntity update(FileEntity file) {
        if (file != null) {
            fileRepository.save(file);
            return file;
        }
        return null;
    }

    @Override
    public List<FileEntity> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public FileEntity findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }
}
