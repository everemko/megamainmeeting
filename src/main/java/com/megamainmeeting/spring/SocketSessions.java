package com.megamainmeeting.spring;

import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import org.springframework.web.socket.WebSocketSession;

public interface SocketSessions {

    void add(long userId, WebSocketSession webSocketSession);

    long getUserId(WebSocketSession session) throws WebSocketSessionNotFoundException;

    WebSocketSession get(long userId);

    void remove(WebSocketSession session);
}
