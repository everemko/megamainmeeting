package domain.entity.auth;

import domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

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
