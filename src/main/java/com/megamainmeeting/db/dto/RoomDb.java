package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.room.Room;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Table(name = "room")
@Entity
public class RoomDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Room_User",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserDb> users = new HashSet<>();
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @OneToMany(mappedBy = "room",  cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ChatMessageDb> messages;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "room")
    private RoomDeleted roomDeleted;

    public void addUser(UserDb userDb) {
        users.add(userDb);
    }

    public void addUsers(Set<UserDb> users) {
        this.users.addAll(users);
    }

    public Room toDomain() {
        Room room = new Room();
        Set<Long> users = this.users
                .stream()
                .map(UserDb::getId)
                .collect(Collectors.toSet());
        room.setId(id);
        room.setUsers(users);
        room.setCreatedAt(createdAt);
        return room;
    }

    public void setRoomDeleted(RoomDeleted roomDeleted) {
        this.roomDeleted = roomDeleted;
    }

    public boolean isUserInRoom(long userId){
        return users.stream().anyMatch(it -> it.getId() == userId);
    }
}
