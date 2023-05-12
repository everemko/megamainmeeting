package com.megamainmeeting.domain.registration;

import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.RegistrationExceptions;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

public class RegisterUserUseCase {

    @Inject
    private RegistrationRepository registrationRepository;
    @Inject
    UserRepository userRepository;

    public RegistrationTokenResult handle(NewUser newUser) throws RegistrationExceptions {
        if (newUser.getName() == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_NAME);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneOffset.UTC);
        if (newUser.getDateBirth() == null) throw new RegistrationExceptions(RegistrationErrorStrings.DATE_BIRTH_NO_VALID);
        if (newUser.getDateBirth().isAfter(localDateTime))
            throw new RegistrationExceptions(RegistrationErrorStrings.DATE_BIRTH_NO_18);
        if (newUser.getGender() == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_GENDER);
        if (newUser.getGenderMatch() == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_GENDER_MATCH);

    }
}
