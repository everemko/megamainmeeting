package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;

public interface UserOpensRepository {

    public Room getRoom(long roomId) throws RoomNotFoundException;

    public void updateRoom(Room room) throws RoomNotFoundException, UserNotFoundException, UserNotInRoomException;

    public void updateUserOpens(UserOpens userOpens) throws RoomNotFoundException, UserNotFoundException, UserNotInRoomException;
}
