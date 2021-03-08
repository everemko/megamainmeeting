package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;
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
    public Room create(Set<Long> users) {
        RoomDb roomDb = new RoomDb();
        for(long userId: users){
            UserDb userDb = new UserDb();
            userDb.setId(userId);
            roomDb.addUser(userDb);
            roomDb.addUser(userDb);
        }
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
