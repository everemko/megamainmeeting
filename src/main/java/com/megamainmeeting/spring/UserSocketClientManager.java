package com.megamainmeeting.spring;

import com.megamainmeeting.spring.base.BaseRpc;
import org.springframework.web.socket.WebSocketSession;

public interface UserSocketClientManager {

    void send(long userId, BaseRpc response);

    void send(WebSocketSession session, BaseRpc response);
}
