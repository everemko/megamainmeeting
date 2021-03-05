package com.megamainmeeting.spring.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class FailureRpcResponse extends BaseRpc {

    private Error error;

    public FailureRpcResponse(String message){
        this.error = new Error(message);
    }

    @AllArgsConstructor
    @Getter
    private static class Error {
        private final String message;
    }
}
