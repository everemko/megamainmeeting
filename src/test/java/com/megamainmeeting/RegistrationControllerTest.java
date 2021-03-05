package com.megamainmeeting;

import com.megamainmeeting.dto.RegistrationResult;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.registration.RegistrationController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Test
    public void testAnonymousRegistration() {
        BaseResponse<RegistrationResult> response = registrationController.getAnonymous();
        assertTrue(response.isSuccess());
        assertNull("", response.getErrorMessage());
        assertNotNull(response.getResult());
        assertTrue(response.getResult().getUserId() >= 0);
    }
}
