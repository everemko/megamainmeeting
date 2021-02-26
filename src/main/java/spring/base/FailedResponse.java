package spring.base;
public class FailedResponse extends BaseResponse<Object> {

    public FailedResponse(String message) {
        super(message, false, null);
    }
}
