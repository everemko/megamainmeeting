package com.megamainmeeting.database.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "one_value")
@Entity
public class OneValueDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;
}
