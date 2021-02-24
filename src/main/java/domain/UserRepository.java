package domain;

import domain.entity.user.User;

public interface UserRepository {

    void saveUser(User user);

    User get(long id);
}
