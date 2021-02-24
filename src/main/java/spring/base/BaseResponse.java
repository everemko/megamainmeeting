package spring.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
public class BaseResponse<T> {

    private static final BaseResponse<Void> SIMPLE_SUCCESS_RESPONSE = new BaseResponse<>(null, true, null);

    @Getter
    private final String errorMessage;
    @Getter
    private final boolean isSuccess;
    @Getter
    private final T result;

//    public static BaseResponse<Void> getErrorResponse(Error error){
//        return new BaseResponse<>(error.getErrorText(), false, null);
//    }

    public static BaseResponse<Void> getSimpleSuccessResponse(){
        return SIMPLE_SUCCESS_RESPONSE;
    }
}
