package com.lisitsin.simpleRestWithSpring.repository;

import com.lisitsin.simpleRestWithSpring.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
