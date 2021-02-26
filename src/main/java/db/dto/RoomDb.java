package db.dto;

import domain.entity.chat.Room;
import domain.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Table(name = "rooms")
@Entity
public class RoomDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "Room_User",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id")}
    )
    private Set<UserDb> users = new HashSet<>();

    public void addUser(UserDb userDb) {
        users.add(userDb);
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
