package com.megamainmeeting.spring.controller.push;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.push.UserPushTokenRepository;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.spring.controller.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Endpoints.BASE_API)
public class PushController {

    @Autowired
    UserPushTokenRepository userPushTokenRepository;

    @PostMapping("/push/token")
    public BaseResponse<Object> addPushToken(@RequestHeader("UserId") long userId,
                                           @RequestBody PushTokenResponseBody body) throws UserNotFoundException, BadDataException {
        userPushTokenRepository.addToken(userId, body.getToken());
        return SuccessResponse.getSimpleSuccessResponse();
    }
}
