package com.megamainmeeting.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Setter
@Getter
@Table(name = "open_request")
@Entity
public class OpenRequestDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomDb room;

    @OneToMany(mappedBy = "openRequest", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserOpenUpDb> userOpen;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);

    public long getRoomId(){
        return room.getId();
    }

    public void addUserOpen(UserOpenUpDb userOpenUpDb){
        userOpen.add(userOpenUpDb);
    }

    @PreRemove
    private void preRemove(){
        room.getOpenRequest().remove(this);
    }
}
