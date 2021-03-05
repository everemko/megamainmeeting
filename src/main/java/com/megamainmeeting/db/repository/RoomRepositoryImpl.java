package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotChatMatchException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.domain.error.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final RoomRepositoryJpa roomRepositoryJpa;

    @Override
    public Room get(long id) {
        return roomRepositoryJpa.findById(id)
                .orElseThrow(NotFoundException::new)
                .toDomain();
    }

    @Override
    public Room create(long user1, long user2) {
        UserDb userDb1 = new UserDb();
        userDb1.setId(user1);
        UserDb userDb2 = new UserDb();
        userDb2.setId(user2);
        RoomDb roomDb = new RoomDb();
        roomDb.addUser(userDb1);
        roomDb.addUser(userDb2);
        roomRepositoryJpa.save(roomDb);
        return roomDb.toDomain();
    }

    @Override
    public List<Room> getList(long userId) {
        return roomRepositoryJpa.findAllByUserId(userId)
                .stream()
                .map(RoomDb::toDomain)
                .collect(Collectors.toList());
    }
}
