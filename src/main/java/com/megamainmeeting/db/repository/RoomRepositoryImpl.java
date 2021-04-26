package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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
        }
        roomRepositoryJpa.save(roomDb);
        return roomDb.toDomain();
    }

    @Override
    public RoomList getList(long userId) {
        List<Room> list = roomRepositoryJpa.findAllByUserId(userId)
                .stream()
                .map(RoomDb::toDomain)
                .collect(Collectors.toList());
        return new RoomList(list);
    }

    @Override
    public void delete(long id) {
        Optional<RoomDb> roomDbOptional = roomRepositoryJpa.findById(id);
        if(roomDbOptional.isEmpty()) return;
        RoomDb roomDb = roomDbOptional.get();
        roomDb.getUsers().clear();
        roomDb.getUserOpens().clear();
        roomRepositoryJpa.save(roomDb);
        roomRepositoryJpa.delete(roomDb);
    }
}
