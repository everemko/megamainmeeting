package com.megamainmeeting;

import com.megamainmeeting.auth.AuthenticationInteractor;
import com.megamainmeeting.auth.AuthenticationInteractorImpl;
import com.megamainmeeting.auth.AuthenticationSessionsRepository;
import com.megamainmeeting.auth.AuthenticationUserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.Authentication;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class AuthenticationInteractorTest {

    private final AuthenticationInteractor authenticationInteractor = new AuthenticationInteractorImpl();

    @Test
    public void testPositive() throws UserNotFoundException {
        Authentication authentication = new Authentication();
        authentication.setToken(AuthenticationTestContext.EXIST_USER_TOKEN_1);
        authentication.setUserId(AuthenticationTestContext.EXIST_USER_ID);
        authenticationInteractor.isAuth(authentication);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUserNotFound() throws UserNotFoundException {
        Authentication authentication = new Authentication();
        authentication.setToken(AuthenticationTestContext.EXIST_USER_TOKEN_1);
        authentication.setUserId(AuthenticationTestContext.NOT_EXIST_USER_ID);
        authenticationInteractor.isAuth(authentication);
    }

    @Test
    public void testUserNotAuth() throws UserNotFoundException {
        Authentication authentication = new Authentication();
        authentication.setToken("");
        authentication.setUserId(AuthenticationTestContext.EXIST_USER_ID);
        Assert.assertFalse(authenticationInteractor.isAuth(authentication));
    }

    static class UserRepositoryImpl implements AuthenticationUserRepository {

        @Override
        public boolean isExist(long userId) {
            return userId == AuthenticationTestContext.EXIST_USER_ID;
        }
    }

    static class AuthenticationSessionsRepositoryImpl implements AuthenticationSessionsRepository {

        @Override
        public Set<String> getTokens(long userId) {
            return AuthenticationTestContext.EXIST_USER_TOKENS;
        }
    }
}
