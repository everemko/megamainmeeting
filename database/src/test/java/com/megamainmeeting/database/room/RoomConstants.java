package com.megamainmeeting.database.room;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.megamainmeeting.database.user.UserConstants.*;

public class RoomConstants {

    public static final Set<Long> USERS_1_2 = new HashSet<>(Arrays.asList(USER_1.getId(), USER_2.getId()));
    public static final Set<Long> USERS_3_4 = new HashSet<>(Arrays.asList(USER_3.getId(), USER_4.getId()));
}
