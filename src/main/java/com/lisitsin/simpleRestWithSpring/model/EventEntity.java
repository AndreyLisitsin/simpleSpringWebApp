package com.lisitsin.simpleRestWithSpring.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "events")
@Data
public class EventEntity extends BaseEntity{


    @OneToOne(mappedBy = "event")
    private FileEntity file;

    @Column(name = "event_name")
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
