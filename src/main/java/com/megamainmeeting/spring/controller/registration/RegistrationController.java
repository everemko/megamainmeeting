package com.megamainmeeting.spring.controller.registration;

import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.domain.error.RegistrationExceptions;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.domain.registration.NewAnonumousUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.spring.dto.RegistrationResult;

@RestController
@AllArgsConstructor
public class RegistrationController {

    public final static String REGISTER_ANONYMOUS_PATH = "/register/anonymous";
    private final Logger logger;
    private final RegistrationRepository registrationRepository;

    @PostMapping(REGISTER_ANONYMOUS_PATH)
    public BaseResponse<RegistrationResult> getAnonymous(@RequestBody NewAnonumousUser newAnonumousUser) throws RegistrationExceptions {
        newAnonumousUser.checkValid();
        Session session = registrationRepository.registerAnon(newAnonumousUser);
        RegistrationResult result = new RegistrationResult();
        result.setToken(session.getToken());
        result.setUserId(session.getUserId());
        return new SuccessResponse<>(result);
    }
}
