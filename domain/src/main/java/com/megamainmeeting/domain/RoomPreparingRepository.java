package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.entity.chat.RoomPreparing;

public interface RoomPreparingRepository {

    RoomPreparing get(long userId) throws UserNotMatchException;

    void remove(long userId);

    void add(RoomPreparing roomPreparing);
}
