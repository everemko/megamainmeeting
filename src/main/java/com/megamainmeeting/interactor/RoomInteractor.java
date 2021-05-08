package com.megamainmeeting.interactor;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomBlockedDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.RoomDeleted;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.RoomIsBlockedException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.dto.RoomResponse;
import com.megamainmeeting.spring.controller.room.RoomBlock;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoomInteractor {

    private final RoomRepositoryJpa roomRepositoryJpa;
    private final UserNotifier userNotifier;

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

    public void blockRoom(
            RoomBlock roomBlock
    ) throws RoomNotFoundException, UserNotFoundException, UserNotInRoomException, Exception {
        roomBlock.checkValid();
        RoomDb roomDb = roomRepositoryJpa.findById(roomBlock.getRoomId()).orElseThrow(RoomNotFoundException::new);
        if(roomDb.getRoomBlocked() != null) throw new RoomIsBlockedException();
        UserDb userDb = roomDb.getUser(roomBlock.getUserId());
        RoomBlockedDb roomBlockedDb = new RoomBlockedDb();
        roomBlockedDb.setRoom(roomDb);
        roomBlockedDb.setUser(userDb);
        roomBlockedDb.setReason(roomBlock.getReason());
        roomDb.setRoomBlocked(roomBlockedDb);
        roomRepositoryJpa.save(roomDb);
        for(UserDb user: roomDb.getUsers()){
            userNotifier.notifyRoomBlocked(user.getId(), roomDb.getId());
        }
    }
}
