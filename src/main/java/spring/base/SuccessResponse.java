package spring.base;

public class SuccessResponse<T> extends BaseResponse<T> {

    private static final BaseResponse<Object> SIMPLE_SUCCESS_RESPONSE = new SuccessResponse<>( null);

    public SuccessResponse(T result) {
        super(null, true, result);
    }

    public static BaseResponse<Object> getSimpleSuccessResponse(){
        return SIMPLE_SUCCESS_RESPONSE;
    }
}
