package domain;

import domain.entity.chat.Room;
import domain.entity.user.User;

import java.util.NoSuchElementException;

public interface RoomRepository {

    Room get(long id) throws NoSuchElementException;

    Room create(long user1, long user2);
}
