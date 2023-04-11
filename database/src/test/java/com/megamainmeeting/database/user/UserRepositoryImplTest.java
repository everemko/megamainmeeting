package com.megamainmeeting.database.user;

import com.megamainmeeting.database.Application;
import com.megamainmeeting.database.repository.UserRepositoryImpl;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryImplTest {

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Test
    public void saveUser() throws UserNotFoundException {
        userRepository.saveUser(UserConstants.USER_1);
        boolean isUserExist = userRepository.isExist(UserConstants.USER_1.getId());
        User userSaved = userRepository.getById(UserConstants.USER_1.getId());
        Assert.assertTrue(isUserExist);
        Assert.assertEquals(UserConstants.USER_1.getId(), userSaved.getId());
        Assert.assertEquals(UserConstants.USER_1.getDateBirth(), userSaved.getDateBirth());
        Assert.assertEquals(UserConstants.USER_1.getGender(), userSaved.getGender());
        Assert.assertEquals(UserConstants.USER_1.getName(), userSaved.getName());

    }

    @Test(expected = UserNotFoundException.class)
    public void getNotExistUser() throws UserNotFoundException{
        boolean isExist = userRepository.isExist(UserConstants.USER_1.getId());
        Assert.assertFalse(isExist);
        userRepository.getById(UserConstants.USER_1.getId());
    }
}
