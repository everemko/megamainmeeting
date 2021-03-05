package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.chat.Room;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
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

    public void addUser(UserDb userDb) {
        users.add(userDb);
    }

    public void addUsers(Set<UserDb> users){
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
        return room;
    }
}
