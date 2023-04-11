package com.megamainmeeting.database.user;

import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.entity.user.User;

import java.time.LocalDateTime;

public class UserConstants {

    public static final User USER_1 = new User(
            1,  Gender.MALE, "Vasia", LocalDateTime.now(),Gender.FEMALE
    );

    public static final User USER_2 = new User(
            2,  Gender.FEMALE, "Maria", LocalDateTime.now(), Gender.MALE
    );

    public static final User USER_3 = new User(
            3,  Gender.MALE, "Antonio", LocalDateTime.now(), Gender.MALE
    );

    public static final User USER_4 = new User(
            4,  Gender.FEMALE, "Katya", LocalDateTime.now(), Gender.MALE
    );
}
