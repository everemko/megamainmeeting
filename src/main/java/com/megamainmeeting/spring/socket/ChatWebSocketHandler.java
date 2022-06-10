package com.megamainmeeting.spring.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.domain.error.ErrorMessages;
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

    private final String TAG = this.getClass().getSimpleName();
    private static final String CONNECTION_CLOSED_MESSAGE = "Connection closed for userId: %s";

    @Autowired
    private ObjectMapper mapper;
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

            if (request.getMethod().equals(RpcMethods.USER_AUTHENTICATION)) {
                AuthenticationSocketDto dto = mapper.convertValue(request.getParams(), AuthenticationSocketDto.class);
                Object response = authenticationController.auth(dto, session);
                BaseRpc rpc = rpcFactory.getSuccess(request.getMethod(), response, request.getId());
                userSocketManager.send(session, rpc);
            } else {
                Object response = null;
                long userId = socketSessions.getUserId(session);
                switch (request.getMethod()) {
                    case RpcMethods.READY_TO_CHAT_STATUS: {
                        ReadyStatusDto dto = mapper.convertValue(request.getParams(), ReadyStatusDto.class);
                        chatCandidateController.handle(dto, userId);
                        break;
                    }
                    case RpcMethods.MESSAGE_HAS_BEEN_READ: {
                        ReadMessageOperationDto dto = mapper.convertValue(request.getParams(), ReadMessageOperationDto.class);
                        messageOperationsController.handle(dto, userId);
                        break;
                    }
                }
                if (response != null) {
                    BaseRpc rpc = rpcFactory.getSuccess(request.getMethod(), response, request.getId());
                    userSocketManager.send(session, rpc);
                }
            }
        } catch (IOException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.DESERIALIZE_ERROR);
            userSocketManager.send(session, response);
        } catch (WebSocketSessionNotFoundException exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.AUTHORIZATION_ERROR);
            userSocketManager.send(session, response);
        } catch (BaseException exception) {
            BaseRpc response = rpcFactory.getError(exception.getMessage());
            userSocketManager.send(session, response);
        } catch (Exception exception) {
            BaseRpc response = rpcFactory.getError(ErrorMessages.INTERNAL_SERVER_ERROR);
            userSocketManager.send(session, response);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        try {
            long userId = socketSessions.getUserId(session);
            logger.info(String.format(CONNECTION_CLOSED_MESSAGE, userId));
        } catch (Throwable ignored) {

        } finally {
            socketSessions.remove(session);
        }
    }
}
