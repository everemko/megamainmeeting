package com.megamainmeeting.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_push_token")
@Getter
@Setter
public class UserPushTokenDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDb userDb;

    private String token;

    public static UserPushTokenDb getInstance(UserDb userDb, String token){
        UserPushTokenDb userPushToken = new UserPushTokenDb();
        userPushToken.setToken(token);
        userPushToken.setUserDb(userDb);
        return userPushToken;
    }
}
