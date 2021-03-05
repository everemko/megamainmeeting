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
    private final SessionRepository sessionRepository;

    public List<Room> getRooms(String token) throws SessionNotFoundException {
        User user = sessionRepository.getUser(token);
        return roomRepository.getList(user.getId());
    }
}
