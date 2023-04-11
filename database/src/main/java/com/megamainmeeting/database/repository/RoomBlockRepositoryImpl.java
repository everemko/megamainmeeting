package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.RoomBlockedRepositoryJpa;
import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.domain.block.NewRoomBlock;
import com.megamainmeeting.domain.block.RoomBlock;
import com.megamainmeeting.domain.block.RoomBlockRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.database.dto.RoomBlockedDb;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.database.dto.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomBlockRepositoryImpl implements RoomBlockRepository {

    @Autowired
    RoomBlockedRepositoryJpa roomBlockedRepositoryJpa;
    @Autowired
    RoomRepositoryJpa roomRepository;

    @Override
    public RoomBlock findByRoomId(long id) throws RoomNotFoundException {
        RoomBlockedDb roomBlockedDb = roomBlockedRepositoryJpa.findByRoomId(id);
        RoomBlock roomBlock = new RoomBlock();
        RoomDb roomDb = roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
        long[] usersInRoom = roomDb.getUsers().stream().mapToLong(UserDb::getId).toArray();
        if (roomBlockedDb == null) {
            roomBlock.setRoomId(id);
        } else {
            roomBlock.setReason(roomBlockedDb.getReason());
            roomBlock.setRoomId(roomBlockedDb.getRoom().getId());
            roomBlock.setUserId(roomBlockedDb.getUser().getId());
        }
        roomBlock.setUsersInRoom(usersInRoom);
        return roomBlock;
    }

    @Override
    public void blockRoom(NewRoomBlock room) {
        UserDb userDb = new UserDb();
        userDb.setId(room.getUserId());
        RoomDb roomDb = new RoomDb();
        roomDb.setId(room.getRoomId());
        RoomBlockedDb roomBlockedDb = new RoomBlockedDb();
        roomBlockedDb.setReason(room.getReason());
        roomBlockedDb.setRoom(roomDb);
        roomBlockedDb.setUser(userDb);
        roomBlockedRepositoryJpa.save(roomBlockedDb);
    }
}
