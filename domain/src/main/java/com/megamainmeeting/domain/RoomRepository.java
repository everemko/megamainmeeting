package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.chat.Room;

import java.util.List;
import java.util.NoSuchElementException;

public interface RoomRepository {

    Room get(long id) throws RoomNotFoundException;

    Room create(long user1, long user2);

    List<Room> getList(long userId);
}
