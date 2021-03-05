package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.auth.Authentication;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationInteractor {

    private final UserRepository userRepository;

    boolean auth(Authentication auth) throws UserNotFoundException {
        if(userRepository.isExist(auth.getUserId())) throw new UserNotFoundException();
        return true;
    }
}
