package spring.controller.registration;

import domain.RegistrationRepository;
import domain.entity.auth.Session;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.base.BaseResponse;
import spring.base.SuccessResponse;
import spring.dto.RegistrationResult;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final Logger logger;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/register/anonymous")
    private BaseResponse<RegistrationResult> getAnonymous(){
        Session session = registrationRepository.registerAnon();
        RegistrationResult result = new RegistrationResult();
        result.setToken(session.getToken());
        result.setUserId(session.getUserId());
        return new SuccessResponse<>(result);
    }
}
