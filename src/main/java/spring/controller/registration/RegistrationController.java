package spring.controller.registration;

import domain.RegistrationRepository;
import domain.entity.auth.Session;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.base.BaseResponse;
import spring.base.SuccessResponse;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final Logger logger;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/register/anonymous")
    private BaseResponse<String> getAnonymous(){
        Session session = registrationRepository.registerAnon();
        return new SuccessResponse<>(session.getToken());
    }
}
