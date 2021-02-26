package db.repository;

import db.RoomRepositoryJpa;
import db.dto.RoomDb;
import db.dto.UserDb;
import domain.RoomRepository;
import domain.entity.chat.Room;
import domain.entity.user.User;
import domain.error.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
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
}
