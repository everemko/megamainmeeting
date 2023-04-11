package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoomRepositoryImpl implements RoomRepository {

    @Inject
    private RoomRepositoryJpa roomRepositoryJpa;

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
        roomRepositoryJpa.deleteById(id);
    }
}
