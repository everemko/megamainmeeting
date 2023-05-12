package com.megamainmeeting.spring.controller.registration;

import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.RegistrationExceptions;
import com.megamainmeeting.domain.registration.RegisterUserUseCase;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.domain.registration.NewUser;
import com.megamainmeeting.spring.controller.Endpoints;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.spring.dto.RegistrationResult;

import static com.megamainmeeting.spring.controller.Endpoints.REGISTER_ANONYMOUS_PATH;

@RestController
@RequestMapping(path = Endpoints.BASE_API)
@AllArgsConstructor
public class RegistrationController {


    private final Logger logger;
    private final RegisterUserUseCase registerUserUseCase;
    private final SessionRepository sessionRepository;


    @PostMapping(REGISTER_ANONYMOUS_PATH)
    public BaseResponse<RegistrationResult> getAnonymous(@RequestBody NewUser newUser) throws RegistrationExceptions {
        String registerUserUseCase.handle(newUser);
        RegistrationResult result = new RegistrationResult();
        result.setToken(session.getToken());
        result.setUserId(session.getUserId());
        return new SuccessResponse<>(result);
    }
}
