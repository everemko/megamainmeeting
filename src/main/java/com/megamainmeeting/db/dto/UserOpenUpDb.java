package com.megamainmeeting.db.dto;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "user_open_up")
@Entity
public class UserOpenUpDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserDb user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomDb room;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_open_type")
    private UserOpenType userOpenType;
}
