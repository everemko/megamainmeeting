package spring.base;

import lombok.Getter;

@Getter
public class NotificationRpcResponse extends BaseRpc {

    public NotificationRpcResponse(String method){
        super.setMethod(method);
    }
}
