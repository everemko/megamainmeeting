package com.megamainmeeting.spring.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.domain.error.ErrorMessages;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.socket.SocketSessions;
import com.megamainmeeting.spring.socket.UserSocketClientManager;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import com.megamainmeeting.spring.socket.dto.BaseRpc;
import com.megamainmeeting.spring.socket.dto.RpcFactory;
import com.megamainmeeting.spring.socket.dto.RpcMethods;
import com.megamainmeeting.spring.socket.dto.RpcRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.megamainmeeting.spring.dto.AuthenticationSocketDto;

import java.io.IOException;
import java.security.Principal;

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
    private RpcFactory rpcFactory;
    @Autowired
    private SocketHandlerCollection socketHandlerCollection;


    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) {
        try {
            RpcRequest request = mapper.readValue(message.getPayload(), RpcRequest.class);
            logger.info("WEB SOCKET RECEIVE: " + request.toString());

            if (request.getMethod().equals(RpcMethods.USER_AUTHENTICATION)) {
                AuthenticationSocketDto dto = mapper.convertValue(request.getParams(), AuthenticationSocketDto.class);
                authenticationController.auth(dto, session);
                return;
            } else {
                authenticationController.checkAuthorization(session);
            }
            long userId = socketSessions.getUserId(session);

            WebSocketControllerHandler socketHandler = socketHandlerCollection.getWebSocketControllerHandler(request.getMethod());
            Object response = socketHandler.handle(request, userId);

            if (response != null) {
                BaseRpc rpc = rpcFactory.getSuccess(request.getMethod(), response, request.getId());
                userSocketManager.send(session, rpc);
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
