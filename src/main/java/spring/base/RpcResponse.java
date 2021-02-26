package spring.base;

import lombok.Getter;

@Getter
public class RpcResponse<T> extends BaseRpc {

    private Long id;
    private T params;

    public RpcResponse(String method, T params){
        super.setMethod(method);
        this.params = params;
    }
}
