package com.megamainmeeting.spring.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RpcFactory {

    private final ObjectMapper mapper = new ObjectMapper();

    public BaseRpc getRpcRequest(Object params){
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setParams(mapper.valueToTree(params));
        return rpcRequest;
    }

    public BaseRpc getNotification(String method, Object params){
        NotificationRpcResponse<Object> response = new NotificationRpcResponse<>();
        response.setParams(params);
        response.setMethod(method);
        return response;
    }

    public BaseRpc getNotification(String method){
        NotificationRpcResponse<Object> response = new NotificationRpcResponse<>();
        response.setMethod(method);
        return response;
    }

    public BaseRpc getError(String message){
        return new FailureRpcResponse(message);
    }

    public BaseRpc getSuccess(String method, Object params, Long id){
        SuccessRpcResponse<Object> response = new SuccessRpcResponse<Object>();
        response.setId(id);
        response.setParams(params);
        response.setMethod(method);
        return response;
    }
}
