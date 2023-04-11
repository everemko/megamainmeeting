package com.megamainmeeting.spring.socket;

import com.megamainmeeting.spring.socket.dto.BaseRpc;
import org.springframework.web.socket.WebSocketSession;

public interface UserSocketClientManager {

    void send(long userId, BaseRpc response);

    void send(WebSocketSession session, BaseRpc response);

    void send(WebSocketSession session, Object object);
}
