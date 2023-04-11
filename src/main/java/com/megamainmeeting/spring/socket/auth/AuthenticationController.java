package com.megamainmeeting.spring.socket.auth;

import com.megamainmeeting.auth.AuthenticationInteractor;
import com.megamainmeeting.domain.error.AuthorizationException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.spring.dto.AuthenticationSocketDto;
import com.megamainmeeting.entity.Authentication;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.socket.SocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class AuthenticationController {

    @Autowired
    private SocketSessions sessions;
    @Autowired
    private AuthenticationInteractor authenticationInteractor;

    public void auth(AuthenticationSocketDto dto, WebSocketSession session) throws AuthorizationException, UserNotFoundException {
        Authentication authentication = new Authentication();
        authentication.setToken(dto.getToken());
        authentication.setUserId(dto.getUserId());
        if (!authenticationInteractor.isAuth(authentication)) throw new AuthorizationException();
        sessions.add(dto.getUserId(), session);
    }

    public void checkAuthorization(WebSocketSession session) throws AuthorizationException {
        try {
            sessions.getUserId(session);
        } catch (WebSocketSessionNotFoundException exception) {
            throw new AuthorizationException();
        }
    }
}
