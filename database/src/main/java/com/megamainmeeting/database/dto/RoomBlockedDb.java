package com.megamainmeeting.database.dto;

import com.megamainmeeting.domain.block.RoomBlockReason;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@Entity
@Table(name = "room_blocked")
public class RoomBlockedDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserDb user;

    @OneToOne()
    @JoinColumn(name = "room_id")
    private RoomDb room;

    @Enumerated(EnumType.STRING)
    private RoomBlockReason reason;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);

    @PreRemove
    public void preRemove(){
        room.setRoomBlocked(null);
    }
}
