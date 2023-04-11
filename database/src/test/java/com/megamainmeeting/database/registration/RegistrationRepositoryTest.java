package com.megamainmeeting.database.registration;

import com.megamainmeeting.database.Application;
import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.domain.registration.NewAnonumousUser;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.megamainmeeting.database.user.UserConstants.USER_1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegistrationRepositoryTest {

    @Inject
    RegistrationRepository registrationRepository;
    @Inject
    SessionRepository sessionRepository;

    @Test
    public void test() throws SessionNotFoundException {
        NewAnonumousUser newAnonumousUser = new NewAnonumousUser();
        newAnonumousUser.setName(USER_1.getName());
        newAnonumousUser.setDateBirth(USER_1.getDateBirth());
        newAnonumousUser.setGender(USER_1.getGender());
        newAnonumousUser.setGenderMatch(USER_1.getGenderMatch());
        Session session = registrationRepository.registerAnon(newAnonumousUser);
        User userSaved = sessionRepository.getUser(session.getToken());
        Assert.assertEquals(USER_1, session.getUser());
        Assert.assertEquals(USER_1, userSaved);
    }
}
