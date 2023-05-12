package com.megamainmeeting.database.dto;

import com.megamainmeeting.entity.user.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, name = "gender_match")
    @Enumerated(EnumType.STRING)
    private Gender genderMatch;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfileDb userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserOpenUpDb> userOpens;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Room_User",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private Set<RoomDb> rooms = new HashSet<>();

    public UserProfileDb getUserProfile(){
        if(userProfile == null) {
            userProfile = new UserProfileDb();
            userProfile.setUser(this);
        }
        return userProfile;
    }

    public void addUserOpens(UserOpenUpDb userOpenUpDb){
        userOpens.add(userOpenUpDb);
    }

    public void remove(UserOpenUpDb userOpenUpDb){
        userOpens.remove(userOpenUpDb);
    }
}
