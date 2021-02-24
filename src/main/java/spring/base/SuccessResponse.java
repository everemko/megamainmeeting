package spring.base;

public class SuccessResponse<T> extends BaseResponse<T> {

    public SuccessResponse(T result) {
        super(null, true, result);
    }
}
