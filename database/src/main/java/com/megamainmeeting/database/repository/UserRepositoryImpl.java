package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.auth.AuthenticationUserRepository;
import com.megamainmeeting.database.mapper.UserDbMapper;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.entity.user.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;


@Component
public class UserRepositoryImpl implements UserRepository, AuthenticationUserRepository {

    @Inject
    private UserDbMapper userDbMapper;

    @Inject
    private UserRepositoryJpa userRepository;

    @Override
    public void saveUser(User user) {
        UserDb userDb = userDbMapper.mapToUserDb(user);
        userRepository.save(userDb);
    }

    @Override
    public User getById(long id) throws UserNotFoundException {
        UserDb userDb = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userDbMapper.mapToUser(userDb);
    }

    @Override
    public boolean isExist(long userId) {
        return userRepository.existsById(userId);
    }
}
