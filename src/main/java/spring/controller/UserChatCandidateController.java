package spring.controller;

import domain.interactor.UserChatCandidateInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.base.*;

import java.util.NoSuchElementException;

@RestController
public class UserChatCandidateController {

    @Autowired
    private UserChatCandidateInteractor chatCandidateInteractor;

    @PostMapping("chat/candidate/add")
    public BaseResponse<Object> add(BaseRequest baseRequest) {
        try {
            chatCandidateInteractor.add(baseRequest.getUserId());
            return SuccessResponse.getSimpleSuccessResponse();
        } catch (NoSuchElementException exception) {
            return new FailedResponse(FailMessages.USER_NOT_FOUND);
        }
    }
}
