package com.megamainmeeting.spring.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.spring.socket.base.BaseController;
import com.megamainmeeting.spring.socket.dto.RpcRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class SocketHandlerImpl<D, C extends BaseController<D, Object>> implements WebSocketControllerHandler {

    private final Class<D> clazz;
    private final C controller;

    @Autowired
    private ObjectMapper mapper;

    public Object handle(RpcRequest request, long userId) throws Exception {
        D dto = mapper.convertValue(request.getParams(), clazz);
        return controller.handle(dto, userId);
    }
}
