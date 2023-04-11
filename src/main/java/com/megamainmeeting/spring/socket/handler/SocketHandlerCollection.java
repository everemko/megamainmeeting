package com.megamainmeeting.spring.socket.handler;

import com.megamainmeeting.spring.socket.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class SocketHandlerCollection {

    @Autowired
    private ApplicationContext applicationContext;
    private final HashMap<String, WebSocketControllerHandler> map = new HashMap<>();

    @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(BaseController.class)
                .values()
                .forEach(baseController -> {
                    map.put(baseController.getRpcMethod(), new SocketHandlerImpl<>(baseController.getDtoClass(), baseController));
                });
    }

    public WebSocketControllerHandler getWebSocketControllerHandler(String rpcMethod){
        return map.get(rpcMethod);
    }
}
