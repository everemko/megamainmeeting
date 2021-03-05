package com.megamainmeeting.spring.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
abstract public class BaseResponse<T> {

    @Getter
    private final String errorMessage;
    @Getter
    private final boolean isSuccess;
    @Getter
    private final T result;

//    public static BaseResponse<Void> getErrorResponse(Error com.megamainmeeting.domain.error){
//        return new BaseResponse<>(com.megamainmeeting.domain.error.getErrorText(), false, null);
//    }

    public static <T> BaseResponse<T> getSuccessInstance(T result){
        return new SuccessResponse<>(result);
    }
}
