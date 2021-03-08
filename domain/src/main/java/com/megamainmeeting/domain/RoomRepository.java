package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.chat.Room;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface RoomRepository {

    Room get(long id) throws RoomNotFoundException;

    Room create(Set<Long> users);

    List<Room> getList(long userId);
}
