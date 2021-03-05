package com.megamainmeeting.spring.controller.registration;

import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.entity.auth.Session;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.dto.RegistrationResult;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final Logger logger;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/register/anonymous")
    public BaseResponse<RegistrationResult> getAnonymous(){
        Session session = registrationRepository.registerAnon();
        RegistrationResult result = new RegistrationResult();
        result.setToken(session.getToken());
        result.setUserId(session.getUserId());
        return new SuccessResponse<>(result);
    }
}
