package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.domain.block.RoomDeleted;
import com.megamainmeeting.domain.block.RoomDeleteRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.database.dto.RoomDeletedDb;
import com.megamainmeeting.database.dto.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomDeleteRepositoryImpl implements RoomDeleteRepository {

    @Autowired
    private RoomRepositoryJpa roomRepositoryJpa;
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public RoomDeleted findByRoomId(long roomId) throws RoomNotFoundException {
        RoomDb roomDb = roomRepositoryJpa.findById(roomId).orElseThrow(RoomNotFoundException::new);
        RoomDeletedDb roomDeletedDb = roomDb.getRoomDeleted();
        RoomDeleted roomDeleted = new RoomDeleted();
        roomDeleted.setRoomId(roomId);
        roomDeleted.setUsersInRoom(roomDb.toDomain().getUsers());
        if (roomDb.getRoomDeleted() == null) {
            roomDeleted.setRoomDeleted(false);
        } else {
            roomDeleted.setRoomDeleted(true);
            roomDeleted.setUserId(roomDeletedDb.getUser().getId());
        }
        return roomDeleted;
    }

    @Override
    public void deleteRoom(long roomId, long userId) throws RoomNotFoundException, UserNotFoundException {
        RoomDb roomDb = roomRepositoryJpa.findById(roomId).orElseThrow(RoomNotFoundException::new);
        UserDb userDb = userRepositoryJpa.findById(userId).orElseThrow(UserNotFoundException::new);
        if (roomDb.getRoomDeleted() == null) return;
        RoomDeletedDb roomDeletedDb = new RoomDeletedDb();
        roomDeletedDb.setRoom(roomDb);
        roomDeletedDb.setUser(userDb);
        roomRepositoryJpa.save(roomDb);
    }
}
