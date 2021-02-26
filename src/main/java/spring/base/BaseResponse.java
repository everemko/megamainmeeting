package spring.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
abstract public class BaseResponse<T> {

    @Getter
    private final String errorMessage;
    @Getter
    private final boolean isSuccess;
    @Getter
    private final T result;

//    public static BaseResponse<Void> getErrorResponse(Error error){
//        return new BaseResponse<>(error.getErrorText(), false, null);
//    }
}
