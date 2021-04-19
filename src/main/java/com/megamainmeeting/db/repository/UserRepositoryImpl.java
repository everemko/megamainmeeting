package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(UserDb.getInstance(user));
    }

    @Override
    public User get(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .toDomain();
    }

    @Override
    public boolean isExist(long userId) {
        return userRepository.existsById(userId);
    }
}
