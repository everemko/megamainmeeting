package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.OpenRequestNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;

public interface UserOpensRepository {

    public Room getRoom(long roomId) throws RoomNotFoundException, OpenRequestNotFoundException;

    public void updateUserOpens(UserOpens userOpens) throws RoomNotFoundException, UserNotFoundException, UserNotInRoomException, OpenRequestNotFoundException;

    public OpenRequest blockRoom(long roomId) throws RoomNotFoundException;

    public Room getRoomByOpenRequestId(long openRequestId) throws OpenRequestNotFoundException, RoomNotFoundException;
}
