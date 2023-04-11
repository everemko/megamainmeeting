package com.megamainmeeting.spring.socket.handler;

import com.megamainmeeting.spring.socket.dto.RpcRequest;

public interface WebSocketControllerHandler {

    public Object handle(RpcRequest request, long userId) throws Exception;
}
