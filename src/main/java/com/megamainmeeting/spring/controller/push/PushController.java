package com.megamainmeeting.spring.controller.push;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.push.UserPushTokenRepository;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushController {

    @Autowired
    UserPushTokenRepository userPushTokenRepository;

    @PostMapping("/push/token")
    public BaseResponse<Object> addPushToken(@RequestAttribute("UserId") long userId,
                                           @RequestBody String token) throws UserNotFoundException, BadDataException {
        userPushTokenRepository.addToken(userId, token);
        return SuccessResponse.getSimpleSuccessResponse();
    }
}
