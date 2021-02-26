package db.repository;

import domain.RoomRepository;
import domain.entity.chat.Room;

import java.util.*;

public class RoomRepositoryMemory implements RoomRepository {


    private final Map<Long, Room> rooms = new HashMap<>();
    private long id = 0;


    @Override
    public Room get(long id) throws NoSuchElementException {
        Room room = rooms.get(id);
        if(room != null) return room;
        else throw new NoSuchElementException();
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
}
