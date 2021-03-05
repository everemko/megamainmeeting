package com.megamainmeeting.spring.base;
public class FailureResponse extends BaseResponse<Object> {

    public FailureResponse(String message) {
        super(message, false, null);
    }
}
