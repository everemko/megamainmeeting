package spring.controller.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entity.chat.Message;
import domain.entity.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler implements MessageChatManager {

    private final Map<User, WebSocketSession> sessions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        User user = mapper.readValue(message.getPayload(), User.class);
        sessions.put(user, session);
    }


    @Override
    public void sendMessage(Message chatMessage) {
        try {
            TextMessage message = new TextMessage(mapper.writeValueAsString(chatMessage));
            for (WebSocketSession session : sessions.values()) {
                session.sendMessage(message);
            }
        } catch (IOException exception) {

        }


    }
}
