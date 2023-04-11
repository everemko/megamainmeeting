package com.megamainmeeting.database.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@Entity
@Table(name = "room_deleted")
public class RoomDeletedDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserDb user;

    @OneToOne()
    @JoinColumn(name = "room_id")
    private RoomDb room;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
}
