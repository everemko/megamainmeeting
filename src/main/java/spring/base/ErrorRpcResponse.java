package spring.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ErrorRpcResponse {

    private Error error;

    public ErrorRpcResponse(String message){
        this.error = new Error(message);
    }

    @AllArgsConstructor
    @Getter
    private static class Error {
        private final String message;
    }
}
