package com.megamainmeeting.database.mapper;

import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserDbMapper {

    public User mapToUser(UserDb userDb){
        return new User(
                userDb.getId(), userDb.getGender(), userDb.getName(), userDb.getDateBirth(), userDb.getGenderMatch()
        );
    }

    public UserDb mapToUserDb(User user){
        UserDb userDb = new UserDb();
        userDb.setDateBirth(user.getDateBirth());
        userDb.setGender(user.getGender());
        userDb.setGenderMatch(user.getGenderMatch());
        userDb.setName(user.getName());
        return userDb;
    }
}
