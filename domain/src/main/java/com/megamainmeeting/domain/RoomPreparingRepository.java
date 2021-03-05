package com.megamainmeeting.domain;

import com.megamainmeeting.entity.chat.RoomPreparing;

public interface RoomPreparingRepository {

    RoomPreparing get(long userId);

    void remove(long userId);

    void add(RoomPreparing roomPreparing);
}
