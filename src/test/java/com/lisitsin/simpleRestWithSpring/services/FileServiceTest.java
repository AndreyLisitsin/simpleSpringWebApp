package com.lisitsin.simpleRestWithSpring.services;

import com.lisitsin.simpleRestWithSpring.model.EventEntity;
import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.repository.FileRepository;
import com.lisitsin.simpleRestWithSpring.service.impl.FileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    private List<FileEntity> list = new ArrayList<>();

    @Before
    public void init(){
        fileRepository = Mockito.mock(FileRepository.class);
        fileService = new FileServiceImpl(fileRepository);

        FileEntity firstFile = new FileEntity();
        firstFile.setId(1L);
        firstFile.setName("text.txt");
        firstFile.setStatus(Status.ACTIVE);
        firstFile.setEvent(new EventEntity());
        list.add(firstFile);

        FileEntity secondFile = new FileEntity();
        secondFile.setId(2L);
        secondFile.setName("text.txt");
        secondFile.setStatus(Status.ACTIVE);
        secondFile.setEvent(new EventEntity());
        list.add(secondFile);
    }

    @Test
    public void saveFileSuccess(){
        FileEntity fileBefore = new FileEntity();
        fileBefore.setName("text.txt");
        fileBefore.setStatus(Status.ACTIVE);
        fileBefore.setEvent(new EventEntity());

        FileEntity fileAfter = list.get(0);
        fileAfter.setFilePath("http://localhost:8075/api/v1/download/text.txt");

        Mockito.when(fileRepository.save(fileBefore)).thenReturn(fileAfter);

        FileEntity file = fileService.save(fileBefore, "text.txt");
        file.setFilePath("http://localhost:8075/api/v1/download/text.txt");
        Assert.assertEquals(fileAfter, file);
    }


    @Test(expected = RuntimeException.class)
    public void saveFileThrowsException(){
        FileEntity fileBefore = new FileEntity();
        fileBefore.setName("text.txt");
        fileBefore.setStatus(Status.ACTIVE);

        FileEntity fileAfter = list.get(0);

        Mockito.when(fileRepository.save(fileBefore)).thenReturn(fileAfter);
        FileEntity file = fileService.save(fileBefore, "text.txt");
    }

    @Test
    public void getFileById(){
        FileEntity file = list.get(0);
        Mockito.when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        FileEntity fileFromDb = fileService.findById(1L);
        Assert.assertEquals(fileFromDb, file);
    }

    @Test
    public void getAllFiles(){
        List<FileEntity> files = list;
        Mockito.when(fileRepository.findAll()).thenReturn(files);
        List<FileEntity> filesFromDb = fileService.getAll();
        Assert.assertEquals(list, filesFromDb);
    }

    @Test
    public void updateFile(){
        FileEntity fileBefore = list.get(0);
        FileEntity fileAfter = list.get(0);
        fileAfter.setName("Diplom.txt");
        Mockito.when(fileRepository.save(fileBefore)).thenReturn(fileAfter);
        FileEntity fileFromDb = fileService.update(fileBefore);
        Assert.assertEquals(fileAfter, fileFromDb);
    }
}
