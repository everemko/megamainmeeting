package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RoomInteractor {

    private final RoomRepository roomRepository;

    public List<Room> getRooms(long userId) {
        return roomRepository.getList(userId);
    }
}
