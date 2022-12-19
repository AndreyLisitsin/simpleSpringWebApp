package com.lisitsin.simpleRestWithSpring.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Data
public class FileEntity extends BaseEntity{

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @ToString.Exclude
    @OneToOne(mappedBy = "file")
    private EventEntity event;
}
