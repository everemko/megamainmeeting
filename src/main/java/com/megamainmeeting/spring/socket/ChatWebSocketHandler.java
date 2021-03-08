package com.megamainmeeting.spring.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.error.ErrorMessages;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.SocketSessions;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import com.megamainmeeting.spring.socket.chat.ChatCandidateController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.megamainmeeting.spring.base.*;
import com.megamainmeeting.dto.AuthenticationSocketDto;
import com.megamainmeeting.dto.ReadyStatusDto;

import java.io.IOException;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {


    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private Logger logger;
    @Autowired
    private UserSocketClientManager userSocketManager;
    @Autowired
    private UserChatCandidateInteractor userChatCandidateInteractor;
    @Autowired
    private SocketSessions socketSessions;
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private ChatCandidateController chatCandidateController;
    @Autowired
    private RpcFactory rpcFactory;
    @Autowired
    ChatMessageOperationsController messageOperationsController;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            RpcRequest request = mapper.readValue(message.getPayload(), RpcRequest.class);
            logger.info("WEB SOCKET RECEIVE: " + request.toString());
            if (!request.getMethod().equals(RpcMethods.USER_AUTHENTICATION)) {
                authenticationController.checkAuthorization(session);
            }
            switch (request.getMethod()) {
                case RpcMethods.USER_AUTHENTICATION: {
                    AuthenticationSocketDto dto = mapper.convertValue(request.getParams(), AuthenticationSocketDto.class);
                    authenticationController.auth(dto, session);
                    break;
                }
                case RpcMethods.READY_TO_CHAT_STATUS: {
                    ReadyStatusDto dto = mapper.convertValue(request.getParams(), ReadyStatusDto.class);
                    chatCandidateController.userStatus(dto, session);
                    break;
                }
                case RpcMethods.MESSAGE_HAS_BEEN_READ: {
                    ReadMessageOperationDto dto = mapper.convertValue(request.getId(), ReadMessageOperationDto.class);
                    messageOperationsController.handle(dto, socketSessions.getUserId(session));
                    break;
                }

            }
        } catch (IOException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.DESERIALIZE_ERROR);
            userSocketManager.send(session, response);
        } catch (AuthorizationException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.AUTHORIZATION_ERROR);
            userSocketManager.send(session, response);
        } catch (UserNotMatchException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.USER_NOT_FOUND_MATCH_ERROR);
            userSocketManager.send(session, response);
        } catch (UserNotFoundException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.USER_NOT_FOUND);
            userSocketManager.send(session, response);
        } catch (WebSocketSessionNotFoundException exception) {

        } catch (ChatMessageNotFoundException exception){
            BaseRpc response = rpcFactory.getError(ErrorMessages.CHAT_MESSAGE_NOT_FOUND);
            userSocketManager.send(session, response);
        } catch (UserNotInRoomException exception){
            BaseRpc response = rpcFactory.getError(ErrorMessages.USER_NOT_IN_ROOM);
            userSocketManager.send(session, response);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        socketSessions.remove(session);
    }
}
