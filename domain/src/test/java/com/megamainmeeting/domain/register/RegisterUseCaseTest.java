package com.megamainmeeting.domain.register;

import com.megamainmeeting.domain.error.RegistrationExceptions;
import com.megamainmeeting.domain.registration.NewUser;
import com.megamainmeeting.domain.registration.RegisterUserUseCase;
import com.megamainmeeting.entity.user.Gender;
import org.junit.Test;

import java.time.LocalDateTime;

public class RegisterUseCaseTest {

    RegisterUserUseCase userUseCase = new RegisterUserUseCase();
    @Test
    public void test() throws RegistrationExceptions {
        NewUser newUser = new NewUser();
        newUser.setName("name");
        newUser.setDateBirth(LocalDateTime.now());
        newUser.setGender(Gender.MALE);
        newUser.setGenderMatch(Gender.FEMALE);
        userUseCase.handle(newUser);
    }
}
