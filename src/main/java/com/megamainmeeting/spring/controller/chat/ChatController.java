package com.megamainmeeting.spring.controller.chat;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.domain.interactor.ChatMessageInteractor;
import com.megamainmeeting.error.ErrorMessages;
import com.megamainmeeting.spring.base.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {


    private final Logger logger;
    private final ChatMessageInteractor chatMessageInteractor;
    private final UserChatCandidateInteractor chatCandidateInteractor;


    @PostMapping("/chat/message/add")
    public BaseResponse<?> processMessage(@RequestBody NewChatMessage newMessage) {
        try {
            logger.info(newMessage.toString());
            ChatMessage message = chatMessageInteractor.onNewMessage(newMessage);
            return BaseResponse.getSuccessInstance(message);
        } catch (RoomNotFoundException exception) {
            return new FailureResponse(ErrorMessages.ROOM_NOT_FOUND);
        } catch (UserNotFoundException exception) {
            return new FailureResponse(ErrorMessages.USER_NOT_FOUND);
        }
    }

    @PostMapping("chat/candidate/add")
    public BaseResponse<Object> addChatCandidate(@RequestBody BaseRequest baseRequest) {
        try {
            chatCandidateInteractor.add(baseRequest.getUserId());
            return SuccessResponse.getSimpleSuccessResponse();
        } catch (UserNotFoundException exception) {
            return new FailureResponse(ErrorMessages.USER_NOT_FOUND);
        } catch (UserAlreadyCandidateException exception){
            return new FailureResponse(ErrorMessages.USER_ALREADY_CANDIDATE);
        }
    }

    @PostMapping("chat/candidate/remove")
    public BaseResponse<Object> removeChatCandidate(@RequestBody BaseRequest request){
        try{
            chatCandidateInteractor.remove(request.getUserId());
            return SuccessResponse.getSimpleSuccessResponse();
        } catch (UserNotFoundException exception){
            return new FailureResponse(ErrorMessages.USER_NOT_FOUND);
        }
    }
}