package com.megamainmeeting.database.useropens;

import com.megamainmeeting.database.repository.UserOpensRepositoryImpl;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.OpenRequestNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.open.Room;
import com.megamainmeeting.domain.open.UserOpensRepository;
import org.junit.Test;

import javax.inject.Inject;

import static com.megamainmeeting.database.room.RoomConstants.USERS_1_2;
import static com.megamainmeeting.database.user.UserConstants.USER_1;
import static com.megamainmeeting.database.user.UserConstants.USER_2;

public class UserOpensRepositoryTest {

    @Inject
    UserOpensRepository userOpensRepository;
    @Inject
    RoomRepository roomRepository;
    @Inject
    UserRepository userRepository;

    @Test
    public void test() throws OpenRequestNotFoundException, RoomNotFoundException {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        roomRepository.create(USERS_1_2);
        Room room = userOpensRepository.getRoom(USER_1.getId());
    }
}
