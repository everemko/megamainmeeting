package com.megamainmeeting;

import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.base.BaseRequest;
import com.megamainmeeting.spring.base.BaseRpc;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class TestClientManager implements UserSocketClientManager {

    private List<BaseRpc> list = new ArrayList<>();

    @Override
    public void send(long userId, BaseRpc response) {
        list.add(response);
    }

    @Override
    public void send(WebSocketSession session, BaseRpc response) {
        list.add(response);
    }

    BaseRpc getFirst(){
        return list.get(0);
    }

    BaseRpc removeFirst(){
        return list.remove(0);
    }
}
