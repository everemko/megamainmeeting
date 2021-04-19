package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.match.RoomPreparing;

public interface RoomPreparingRepository {

    RoomPreparing get(long userId) throws UserNotMatchException;

    void add(RoomPreparing roomPreparing);

    void remove(RoomPreparing roomPreparing);
}
