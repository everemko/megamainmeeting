package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;

import java.util.List;
import java.util.Set;

public interface RoomRepository {

    Room get(long id) throws RoomNotFoundException;

    Room create(Set<Long> users);

    RoomList getList(long userId);

    void delete(long id);
}
