package spring.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import domain.UserMatchNotifier;
import domain.entity.chat.ChatMessage;
import domain.entity.chat.Room;
import domain.error.UserNotChatMatchException;
import domain.interactor.UserChatCandidateInteractor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import spring.base.*;
import spring.controller.chat.MessageChatManager;
import spring.dto.AuthenticationSocketDto;
import spring.dto.ReadyStatusDto;
import spring.dto.RoomReadyResult;

import java.io.IOException;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler implements MessageChatManager, UserMatchNotifier {

    private final BiMap<Long, WebSocketSession> sessions = HashBiMap.create();
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private Logger logger;
    @Autowired UserChatCandidateInteractor userChatCandidateInteractor;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            RpcRequest request = mapper.readValue(message.getPayload(), RpcRequest.class);
            switch (request.getMethod()) {
                case RpcMethods.USER_AUTHENTICATION: {
                    AuthenticationSocketDto dto = mapper.convertValue(request.getParams(), AuthenticationSocketDto.class);
                    sessions.put(dto.getUserId(), session);
                    break;
                }
                case RpcMethods.READY_TO_CHAT_STATUS:{
                    checkAuthorization(session);
                    ReadyStatusDto dto = mapper.convertValue(request.getParams(), ReadyStatusDto.class);
                    if(dto.isReady()){
                        userChatCandidateInteractor.setUserReady(sessions.inverse().get(session));
                    } else{
                        userChatCandidateInteractor.setUserNotReady(sessions.inverse().get(session));
                    }
                    break;
                }
            }
        } catch (IOException exception){
            ErrorRpcResponse response = new ErrorRpcResponse(RpcMethods.DESERIALIZE_ERROR);
            TextMessage result = convert(response);
            if(result != null) sendMessage(session, result);
        } catch (AuthorizationException exception){
            ErrorRpcResponse response = new ErrorRpcResponse(RpcMethods.AUTHORIZATION_ERROR);
            TextMessage result = convert(response);
            if(result != null) sendMessage(session, result);
        } catch (UserNotChatMatchException exception){
            ErrorRpcResponse response = new ErrorRpcResponse(RpcMethods.USER_NOT_FOUND_MATCH_ERROR);
            TextMessage result = convert(response);
            if(result != null) sendMessage(session, result);
        }
    }

    private void checkAuthorization(WebSocketSession session) throws AuthorizationException{
        Long userId = sessions.inverse().get(session);
        if(userId == null) throw new AuthorizationException();
    }

    private void sendMessage(WebSocketSession session, TextMessage result){
        try{
            session.sendMessage(result);
        } catch (IOException exception){
            logger.error(" IOException " + exception.toString());
        }
    }


    @Override
    public void sendMessage(ChatMessage chatMessage) {
        try {
            TextMessage message = new TextMessage(mapper.writeValueAsString(chatMessage));
            for (WebSocketSession session : sessions.values()) {
                session.sendMessage(message);
            }
        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    @Override
    public void sendIgnoreSender(ChatMessage message) {
        try {
            TextMessage response = convert(message);
            Room room = message.getRoom();
            for (long userId: room.getUsers()) {
                if(userId == message.getUserId()) continue;
                sessions.get(userId).sendMessage(response);
            }
        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    @Override
    public void notifyRoomReady(Room room) {
        try {
            RoomReadyResult result = new RoomReadyResult(room.getId());
            RpcResponse<RoomReadyResult> response = new RpcResponse<>(RpcMethods.CHAT_ROOM_CREATED_NOTIFICATION, result);
            for (long user : room.getUsers()) {
                WebSocketSession session = sessions.get(user);
                if(session != null) session.sendMessage(convert(response));
            }

        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    @Override
    public void notifyMatch(UserChatCandidateInteractor.RoomPreparing preparing) {
        try {
            NotificationRpcResponse response = new NotificationRpcResponse(RpcMethods.USER_MATCH_FOUND_NOTIFICATION);
            for (long user : preparing.getUsers()) {
                WebSocketSession session = sessions.get(user);
                if(session != null) session.sendMessage(convert(response));
            }

        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    @Override
    public void notifyUsersRefuse(UserChatCandidateInteractor.RoomPreparing preparing) {
        try {
            NotificationRpcResponse response = new NotificationRpcResponse(RpcMethods.USERS_REFUSE_ROOM);
            for (long user : preparing.getUsers()) {
                WebSocketSession session = sessions.get(user);
                if(session != null) session.sendMessage(convert(response));
            }

        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    private TextMessage convert(Object object){
        try{
            return new TextMessage(mapper.writeValueAsString(object));
        } catch (IOException exception){
            logger.error("Write to TextMessage Error: " + exception.toString());
            return null;
        }
    }

    static class AuthorizationException extends Exception{

    }
}
