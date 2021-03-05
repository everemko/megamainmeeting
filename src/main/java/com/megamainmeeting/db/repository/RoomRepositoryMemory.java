package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.chat.Room;

import java.util.*;
import java.util.stream.Collectors;

public class RoomRepositoryMemory implements RoomRepository {


    private final Map<Long, Room> rooms;
    private long id = 0;

    public RoomRepositoryMemory(){
        rooms = new HashMap<>();
        create(1, 2);
    }


    @Override
    public Room get(long id) throws RoomNotFoundException {
        Room room = rooms.get(id);
        if (room != null) return room;
        else throw new RoomNotFoundException();
    }

    @Override
    public Room create(long user1, long user2) {
        Room room = new Room();
        room.setId(id);
        room.addUser(user1);
        room.addUser(user2);
        rooms.put(id, room);
        id++;
        return room;
    }

    @Override
    public List<Room> getList(long userId) {
        return rooms.values()
                .stream()
                .filter(room -> room.isUserInRoom(userId))
                .collect(Collectors.toList());
    }
}
