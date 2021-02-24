package spring.db.repository;

import domain.UserRepository;
import domain.entity.user.User;
import lombok.AllArgsConstructor;
import spring.db.UserRepositoryJpa;
import spring.db.dto.UserDto;


@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(UserDto.getInstance(user));
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow().toDomain();
    }
}
