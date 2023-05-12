package com.megamainmeeting.domain.registration;

import com.megamainmeeting.domain.error.RegistrationExceptions;
import com.megamainmeeting.entity.user.Gender;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

//sex 0 - man, 1
@Data
public class NewUser {

    private String name;
    private LocalDateTime dateBirth;
    private Gender gender;
    private Gender genderMatch;
}
