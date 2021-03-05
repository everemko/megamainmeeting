package com.megamainmeeting.memory;

import com.megamainmeeting.domain.RoomPreparingRepository;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.UserNotChatMatchException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Component
public class RoomPreparingRepositoryMemory implements RoomPreparingRepository {

    private final Map<Long, RoomPreparing> roomPreparings = new HashMap<>();
    private final RoomRepository roomRepository;

    @Override
    public RoomPreparing get(long userId) {
        return roomPreparings.get(userId);
    }

    @Override
    public void remove(long userId) {
        roomPreparings.remove(userId);
    }

    @Override
    public void add(RoomPreparing roomPreparing) {
        roomPreparings.put(roomPreparing.getUser1(), roomPreparing);
        roomPreparings.put(roomPreparing.getUser2(), roomPreparing);
    }
}
