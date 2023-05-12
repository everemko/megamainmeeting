package com.megamainmeeting.domain.block;


import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.block.entity.NewRoomBlock;
import com.megamainmeeting.domain.block.entity.RoomBlockReason;
import com.megamainmeeting.domain.error.RoomIsBlockedException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.megamainmeeting.domain.block.RoomBlockInteractorTestContext.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RoomBlockInteractorTest {

    private final RoomBlockTestRepository roomBlockTestRepository = new RoomBlockTestRepository();
    private final RoomDeleteTestRepository roomDeleteTestRepository = new RoomDeleteTestRepository();
    private final UserNotifier userNotifier = Mockito.mock(UserNotifier.class);
    private final RoomInteractor roomInteractor = new RoomInteractor();

    @Before
    public void clean(){
        roomBlockTestRepository.setRoomBlocked(false);
    }

    @Test(expected = RoomNotFoundException.class)
    public void testRoomNotFound() throws Exception {
        NewRoomBlock newRoomBlock = new NewRoomBlock();
        newRoomBlock.setRoomId(1);
        newRoomBlock.setReason(RoomBlockReason.Other);
        newRoomBlock.setUserId(EXIST_USER);
        roomInteractor.blockRoom(newRoomBlock);
        for (long userId: USERS_IN_ROOM) {
            verify(userNotifier, times(0)).notifyRoomBlocked(userId, newRoomBlock);
        }
    }

    @Test(expected = RoomIsBlockedException.class)
    public void testRoomAlreadyBlocked() throws Exception {
        roomBlockTestRepository.setRoomBlocked(true);
        NewRoomBlock newRoomBlock = new NewRoomBlock();
        newRoomBlock.setRoomId(EXIST_ROOM);
        newRoomBlock.setReason(RoomBlockReason.Other);
        newRoomBlock.setUserId(EXIST_USER);
        roomInteractor.blockRoom(newRoomBlock);
        for (long userId: USERS_IN_ROOM) {
            verify(userNotifier, times(0)).notifyRoomBlocked(userId, newRoomBlock);
        }
    }

    @Test(expected = UserNotInRoomException.class)
    public void testUserNotInRoom() throws Exception {
        NewRoomBlock newRoomBlock = new NewRoomBlock();
        newRoomBlock.setRoomId(EXIST_ROOM);
        newRoomBlock.setReason(RoomBlockReason.Other);
        newRoomBlock.setUserId(1);
        roomInteractor.blockRoom(newRoomBlock);
        for (long userId: USERS_IN_ROOM) {
            verify(userNotifier, times(0)).notifyRoomBlocked(userId, newRoomBlock);
        }
    }

    @Test
    public void testPositive() throws Exception {
        NewRoomBlock newRoomBlock = new NewRoomBlock();
        newRoomBlock.setRoomId(EXIST_ROOM);
        newRoomBlock.setUserId(EXIST_USER);
        newRoomBlock.setReason(RoomBlockReason.Other);
        roomInteractor.blockRoom(newRoomBlock);
        for (long userId: USERS_IN_ROOM) {
            verify(userNotifier, times(1)).notifyRoomBlocked(userId, newRoomBlock);
        }
    }
}
