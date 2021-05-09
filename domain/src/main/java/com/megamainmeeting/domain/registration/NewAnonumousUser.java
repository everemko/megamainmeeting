package com.megamainmeeting.domain.registration;

import com.megamainmeeting.domain.error.RegistrationExceptions;
import com.megamainmeeting.entity.user.Gender;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

//sex 0 - man, 1
@Data
public class NewAnonumousUser {

    private String name;
    private LocalDateTime dateBirth;
    private Gender gender;
    private Gender genderMatch;

    public void checkValid() throws RegistrationExceptions {
        if(name == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_NAME);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneOffset.UTC);
        if(dateBirth == null) throw new RegistrationExceptions(RegistrationErrorStrings.DATE_BIRTH_NO_VALID);
        if(dateBirth.isAfter(localDateTime)) throw new RegistrationExceptions(RegistrationErrorStrings.DATE_BIRTH_NO_18);
        if(gender == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_GENDER);
        if(genderMatch == null) throw new RegistrationExceptions(RegistrationErrorStrings.NO_GENDER_MATCH);
    }
}
