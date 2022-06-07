package com.megamainmeeting.spring.socket.auth;

import com.megamainmeeting.auth.AuthenticationInteractor;
import com.megamainmeeting.domain.error.AuthorizationException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.dto.AuthenticationSocketDto;
import com.megamainmeeting.entity.auth.Authentication;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.SocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class AuthenticationController {

    @Autowired
    private SocketSessions sessions;
    @Autowired
    private AuthenticationInteractor authenticationInteractor;

    public boolean auth(AuthenticationSocketDto dto, WebSocketSession session) throws UserNotFoundException {
        Authentication authentication = new Authentication();
        authentication.setToken(dto.getToken());
        authentication.setUserId(dto.getUserId());
        if (!authenticationInteractor.isAuth(authentication)) return false;
        sessions.add(dto.getUserId(), session);
        return true;
    }

    public void checkAuthorization(WebSocketSession session) throws AuthorizationException {
        try {
            Long userId = sessions.getUserId(session);
            if (userId == null) throw new AuthorizationException();
        } catch (WebSocketSessionNotFoundException exception) {
            throw new AuthorizationException();
        }
    }
}
