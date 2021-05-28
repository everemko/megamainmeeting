package com.megamainmeeting.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "box_hosting_user_avatar")
public class BoxHostingUserAvatarDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private String imageUrl;

}
