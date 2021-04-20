package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class UserDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "date_birth")
    private LocalDateTime dateBirth;
    @Column(nullable = false)
    private Long gender;
    @Column(nullable = false, name = "gender_match")
    private Long genderMatch;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfileDb userProfile = new UserProfileDb();

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Room_User",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private Set<RoomDb> rooms = new HashSet<>();

    public User toDomain(){
        LocalDateTime now = LocalDateTime.now();
        long age = Period.between(dateBirth.toLocalDate(), now.toLocalDate()).getYears();
        List<Room> roomList = rooms.stream().map(RoomDb::toDomain).collect(Collectors.toList());
        return new User(id, age, new RoomList(roomList));
    }

    public static UserDb getInstance(User user){
        UserDb dto = new UserDb();
        dto.id = user.getId();
        return dto;
    }
}
