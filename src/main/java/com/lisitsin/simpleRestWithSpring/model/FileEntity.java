package com.lisitsin.simpleRestWithSpring.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
public class FileEntity extends BaseEntity{

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,  CascadeType.REMOVE})
    @JoinTable(name = "events_files",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private EventEntity event;
}
