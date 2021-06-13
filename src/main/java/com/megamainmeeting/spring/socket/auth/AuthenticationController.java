package com.megamainmeeting.spring.socket.auth;

import com.megamainmeeting.domain.error.AuthorizationException;
import com.megamainmeeting.dto.AuthenticationSocketDto;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.SocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class AuthenticationController {

    @Autowired
    private SocketSessions sessions;

    public boolean auth(AuthenticationSocketDto dto, WebSocketSession session){
        sessions.add(dto.getUserId(), session);
        return true;
    }

    public void checkAuthorization(WebSocketSession session) throws AuthorizationException {
        try {
            Long userId = sessions.getUserId(session);
            if (userId == null) throw new AuthorizationException();
        } catch (WebSocketSessionNotFoundException exception){
            throw new AuthorizationException();
        }
    }
}
