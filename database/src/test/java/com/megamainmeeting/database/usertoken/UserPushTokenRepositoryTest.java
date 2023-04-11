package com.megamainmeeting.database.usertoken;

import com.megamainmeeting.database.Application;
import com.megamainmeeting.database.repository.UserPushTokenRepositoryImpl;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.open.User;
import com.megamainmeeting.push.UserPushTokenNotFound;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.*;

import static com.megamainmeeting.database.user.UserConstants.USER_1;
import static com.megamainmeeting.database.user.UserConstants.USER_2;
import static com.megamainmeeting.database.usertoken.UserPushTokenConstants.TOKEN_1;
import static com.megamainmeeting.database.usertoken.UserPushTokenConstants.TOKEN_2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserPushTokenRepositoryTest {

    @Inject
    private UserPushTokenRepositoryImpl userPushTokenRepository;
    @Inject
    UserRepository userRepository;

    @Test
    public void testExistToken() throws UserNotFoundException, BadDataException, UserPushTokenNotFound {
        userRepository.saveUser(USER_1);
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_1);
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_2);
        List<String> tokens = userPushTokenRepository.getTokens(USER_1.getId());
        List<String> tokensExpected = new ArrayList<>();
        tokensExpected.add(TOKEN_1);
        tokensExpected.add(TOKEN_2);
        Assert.assertEquals(tokens, tokensExpected);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUserNotFound() throws UserNotFoundException, BadDataException, UserPushTokenNotFound{
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_1);
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_2);
        List<String> tokens = userPushTokenRepository.getTokens(USER_1.getId());
        List<String> tokensExpected = new ArrayList<>();
        tokensExpected.add(TOKEN_1);
        tokensExpected.add(TOKEN_2);
        Assert.assertEquals(tokens, tokensExpected);
    }

    @Test
    public void testUserPushTokenNotFound() {
        userRepository.saveUser(USER_1);
        List<String> tokens = userPushTokenRepository.getTokens(USER_1.getId());
        Assert.assertTrue(tokens.isEmpty());
    }

    @Test
    public void testGetUsersPushToken() throws BadDataException, UserNotFoundException {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_1);
        userPushTokenRepository.addToken(USER_1.getId(), TOKEN_2);
        userPushTokenRepository.addToken(USER_2.getId(), TOKEN_1);
        userPushTokenRepository.addToken(USER_2.getId(), TOKEN_2);
        Set<Long> users = new HashSet<>();
        users.add(USER_1.getId());
        users.add(USER_2.getId());
        String[] tokensExpected = {TOKEN_1, TOKEN_2};
        Collection<String> tokens = userPushTokenRepository.getTokens(users);
        Assert.assertArrayEquals(tokensExpected, tokens.toArray());
    }
}
