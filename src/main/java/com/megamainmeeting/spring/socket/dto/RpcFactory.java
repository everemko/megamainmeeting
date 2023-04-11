package com.megamainmeeting.spring.socket.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcFactory {

    @Autowired
    private ObjectMapper mapper;

    public BaseRpc getRpcRequest(Object params){
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setParams(mapper.valueToTree(params));
        return rpcRequest;
    }

    public NotificationRpcResponse<Object> getNotification(String method, Object params){
        NotificationRpcResponse<Object> response = new NotificationRpcResponse<>();
        response.setParams(params);
        response.setMethod(method);
        return response;
    }

    public NotificationRpcResponse<Object> getNotification(String method){
        NotificationRpcResponse<Object> response = new NotificationRpcResponse<>();
        response.setMethod(method);
        return response;
    }

    public FailureRpcResponse getError(String message){
        return new FailureRpcResponse(message);
    }

    public SuccessRpcResponse<Object> getSuccess(String method, Object params, Long id){
        SuccessRpcResponse<Object> response = new SuccessRpcResponse<Object>();
        response.setId(id);
        response.setParams(params);
        response.setMethod(method);
        return response;
    }
}
