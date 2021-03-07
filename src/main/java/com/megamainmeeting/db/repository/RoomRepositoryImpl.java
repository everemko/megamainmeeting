package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final RoomRepositoryJpa roomRepositoryJpa;

    @Override
    public Room get(long id) throws RoomNotFoundException {
        return roomRepositoryJpa.findById(id)
                .orElseThrow(RoomNotFoundException::new)
                .toDomain();
    }

    @Override
    public Room create(long user1, long user2) {
        UserDb userDb1 = new UserDb();
        userDb1.setId(user1);
        UserDb userDb2 = new UserDb();
        userDb2.setId(user2);
        RoomDb roomDb = new RoomDb();
        roomDb.addUser(userDb1);
        roomDb.addUser(userDb2);
        roomRepositoryJpa.save(roomDb);
        return roomDb.toDomain();
    }

    @Override
    public List<Room> getList(long userId) {
        return roomRepositoryJpa.findAllByUserId(userId)
                .stream()
                .map(RoomDb::toDomain)
                .collect(Collectors.toList());
    }
}
