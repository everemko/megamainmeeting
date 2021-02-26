package domain;

import domain.entity.user.User;

import java.util.NoSuchElementException;

public interface UserRepository {

    void saveUser(User user);

    User get(long id) throws NoSuchElementException;
}
