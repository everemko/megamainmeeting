package com.megamainmeeting.memory;

import com.megamainmeeting.domain.RoomPreparingRepository;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.match.RoomPreparing;
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
    public void add(RoomPreparing roomPreparing) {
        roomPreparing.getUsers().forEach(it -> {
            roomPreparings.put(it, roomPreparing);
        });
    }

    @Override
    public void remove(RoomPreparing roomPreparing) {
        roomPreparing.getUsers().forEach(it -> {
            roomPreparings.remove(it, roomPreparing);
        });
    }
}
