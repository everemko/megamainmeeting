package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import org.junit.Test;
import org.mockito.Mockito;

import static com.megamainmeeting.domain.block.RoomBlockInteractorTestContext.EXIST_ROOM;
import static com.megamainmeeting.domain.block.RoomBlockInteractorTestContext.EXIST_USER;

public class RoomDeleteInteractorTest {

    private final RoomBlockTestRepository roomBlockTestRepository = new RoomBlockTestRepository();
    private final RoomDeleteTestRepository roomDeleteTestRepository = new RoomDeleteTestRepository();
    private final UserNotifier userNotifier = Mockito.mock(UserNotifier.class);
    private final RoomInteractor roomInteractor = new RoomInteractor();

    @Test(expected = UserNotInRoomException.class)
    public void testDeleteUserNotInRoom() throws Exception {
        roomInteractor.removeRoom(1, EXIST_ROOM);
    }

    @Test
    public void testDeletePositive() throws Exception {
        roomInteractor.removeRoom(EXIST_USER, EXIST_ROOM);
    }
}
