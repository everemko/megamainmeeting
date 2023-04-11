package com.megamainmeeting.spring.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class FailureRpcResponse extends BaseRpcResponse {

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
