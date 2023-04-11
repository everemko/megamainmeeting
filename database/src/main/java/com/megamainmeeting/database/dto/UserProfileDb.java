package com.megamainmeeting.database.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfileDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private UserDb user;

    private String city;
    private String country;
    private String profession;
    private String aboutMyself;
    private int height;
    private int weight;
    private String firstDateIdeal;
}
