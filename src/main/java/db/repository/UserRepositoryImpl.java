package db.repository;

import domain.UserRepository;
import domain.entity.user.User;
import lombok.AllArgsConstructor;
import db.UserRepositoryJpa;
import db.dto.UserDb;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(UserDb.getInstance(user));
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow().toDomain();
    }
}
