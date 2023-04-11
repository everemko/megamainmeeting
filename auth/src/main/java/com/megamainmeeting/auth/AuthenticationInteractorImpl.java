package com.megamainmeeting.auth;

import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.Authentication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import java.util.Set;


public class AuthenticationInteractorImpl implements AuthenticationInteractor{

    @Inject
    private AuthenticationUserRepository userRepository;
    @Inject
    private AuthenticationSessionsRepository sessionRepository;

    public boolean isAuth(Authentication auth) throws UserNotFoundException {
        if (!userRepository.isExist(auth.getUserId())) throw new UserNotFoundException();
        Set<String> tokens = sessionRepository.getTokens(auth.getUserId());
        return tokens.contains(auth.getToken());
    }
}
