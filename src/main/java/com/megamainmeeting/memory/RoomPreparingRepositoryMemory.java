package com.megamainmeeting.memory;

import com.megamainmeeting.domain.RoomPreparingRepository;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.entity.chat.RoomPreparing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class RoomPreparingRepositoryMemory implements RoomPreparingRepository {

    private final Map<Long, RoomPreparing> roomPreparings = new HashMap<>();

    @Override
    public RoomPreparing get(long userId) throws UserNotMatchException {
        RoomPreparing roomPreparing = roomPreparings.get(userId);
        if(roomPreparing == null) throw new UserNotMatchException();
        else return roomPreparing;
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
