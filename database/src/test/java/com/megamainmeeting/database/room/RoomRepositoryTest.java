package com.megamainmeeting.database.room;

import com.megamainmeeting.database.Application;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.megamainmeeting.database.room.RoomConstants.USERS_1_2;
import static com.megamainmeeting.database.user.UserConstants.USER_1;
import static com.megamainmeeting.database.user.UserConstants.USER_2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomRepositoryTest {

    @Inject
    RoomRepository roomRepository;
    @Inject
    UserRepository userRepository;

    @Test
    public void createRoom() throws RoomNotFoundException {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        Room roomCreated = roomRepository.create(USERS_1_2);
        Room roomSaved = roomRepository.get(roomCreated.getId());
        Assert.assertEquals(roomCreated, roomSaved);
    }

    @Test(expected = RoomNotFoundException.class)
    public void checkRoomNotFound() throws RoomNotFoundException {
        roomRepository.get(0);
    }

    @Test(expected = RoomNotFoundException.class)
    public void deleteRoom() throws RoomNotFoundException {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        Room roomCreated = roomRepository.create(USERS_1_2);
        roomRepository.delete(roomCreated.getId());
        roomRepository.get(roomCreated.getId());
    }

    @Test
    public void getRoomList() {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        roomRepository.create(USERS_1_2);
        RoomList roomList = roomRepository.getList(USER_1.getId());
        boolean isFirstUserInRoom = roomList.isUserInRoom(USER_1.getId());
        boolean isSecondUserInRoom = roomList.isUserInRoom(USER_2.getId());
        Assert.assertTrue(isFirstUserInRoom);
        Assert.assertTrue(isSecondUserInRoom);
    }
}
