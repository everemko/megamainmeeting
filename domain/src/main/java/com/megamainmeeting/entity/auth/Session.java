package com.megamainmeeting.entity.auth;

import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Session {

    @Getter
    private long id;

    @Getter
    private String token;

    @Getter
    private User user;

    public long getUserId(){
        return user.getId();
    }
}
