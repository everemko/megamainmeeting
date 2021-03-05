package com.megamainmeeting.memory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.megamainmeeting.spring.SocketSessions;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SocketSessionsImpl implements SocketSessions {

    private final BiMap<Long, WebSocketSession> sessions = HashBiMap.create();

    @Override
    public void add(long userId, WebSocketSession webSocketSession) {
        sessions.put(userId, webSocketSession);
    }

    @Override
    public long getUserId(WebSocketSession session) {
        return sessions.inverse().get(session);
    }

    @Override
    public WebSocketSession get(long userId) {
        return sessions.get(userId);
    }

    @Override
    public void remove(WebSocketSession session) {
        sessions.inverse().remove(session);
    }
}
