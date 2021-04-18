package com.megamainmeeting.interactor;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.RoomDeleted;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.dto.RoomResponse;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoomInteractor {

    private final RoomRepositoryJpa roomRepositoryJpa;

    public List<RoomResponse> getRooms(long userId) {
        List<RoomDb> rooms = roomRepositoryJpa.findAllByUserId(userId);
        if (rooms == null) return Collections.emptyList();
        return rooms.stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());
    }

    public void removeRoom(long userId, long roomId) throws RoomNotFoundException, UserNotInRoomException {
        Optional<RoomDb> optional = roomRepositoryJpa.findById(roomId);
        if (optional.isEmpty()) {
            throw new RoomNotFoundException();
        }
        RoomDb room = optional.get();
        if (room.getUsers().stream().noneMatch(it -> it.getId() == userId)) {
            throw new UserNotInRoomException();
        }
        UserDb userDb = room.getUsers().stream().filter(it -> it.getId() == userId).findFirst().get();
        RoomDeleted roomDeleted = new RoomDeleted();
        roomDeleted.setUser(userDb);
        roomDeleted.setRoom(room);
        room.setRoomDeleted(roomDeleted);
        roomRepositoryJpa.save(room);
    }
}
