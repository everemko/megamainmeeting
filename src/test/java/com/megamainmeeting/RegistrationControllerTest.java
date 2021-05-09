package com.megamainmeeting;

import com.megamainmeeting.domain.registration.NewAnonumousUser;
import com.megamainmeeting.dto.RegistrationResult;
import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.registration.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Test
    public void testAnonymousRegistration() throws Exception{
        NewAnonumousUser user = new NewAnonumousUser();
        user.setGender(Gender.MALE);
        user.setGenderMatch(Gender.FEMALE);
        user.setName("Vasia");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -20);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneOffset.UTC);
        user.setDateBirth(localDateTime);
        BaseResponse<RegistrationResult> response = registrationController.getAnonymous(user);
        assertTrue(response.isSuccess());
        assertNull("", response.getErrorMessage());
        assertNotNull(response.getResult());
        assertTrue(response.getResult().getUserId() >= 0);
    }
}
