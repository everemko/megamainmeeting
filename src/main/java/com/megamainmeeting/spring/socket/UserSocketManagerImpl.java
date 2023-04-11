package com.megamainmeeting.spring.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.spring.socket.dto.BaseRpc;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class UserSocketManagerImpl implements UserSocketClientManager {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Logger logger;
    @Autowired
    private SocketSessions sessions;

    @Override
    public void send(long userId, BaseRpc response) {
        try {
            TextMessage result = new TextMessage(mapper.writeValueAsString(response));
            WebSocketSession session = sessions.get(userId);
            if(session != null) {
                session.sendMessage(result);
                logger.info("WEB SOCKET SEND: to USER " + userId + " " + response);
            } else {
                logger.error("WEB SOCKET SESSION NOT FOUND USER_ID " + userId);
            }
        }  catch (IOException e) {
            logger.error("USER_ID " + userId + " during SEND TO SOCKET: " + response, e);
        }
    }

    @Override
    public void send(WebSocketSession session, BaseRpc response) {
        try {
            TextMessage result = new TextMessage(mapper.writeValueAsString(response));
            session.sendMessage(result);
            logger.info("WEB SOCKET SEND: " + response);
        }  catch (IOException e) {
            logger.error(session + " during SEND TO SOCKET: " + response, e);
        }
    }

    @Override
    public void send(WebSocketSession session, Object object) {
        try {
            TextMessage result = new TextMessage(mapper.writeValueAsString(object));
            session.sendMessage(result);
            logger.info("WEB SOCKET SEND: " + result.toString());
        }  catch (IOException e) {
            logger.error(session + " during SEND TO SOCKET: " + object.toString(), e);
        }
    }
}
