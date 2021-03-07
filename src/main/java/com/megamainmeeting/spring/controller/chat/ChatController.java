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
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ChatController {


    private final Logger logger;
    private final ChatMessageInteractor chatMessageInteractor;
    private final UserChatCandidateInteractor chatCandidateInteractor;


    @PostMapping("/chat/message/add")
    public BaseResponse<?> processMessage(@RequestAttribute("UserId") long userId,
                                          @RequestBody NewChatMessage newMessage) throws Exception {
        newMessage.setUserId(userId);
        ChatMessage message = chatMessageInteractor.onNewMessage(newMessage);
        return BaseResponse.getSuccessInstance(message);
    }

    @PostMapping("chat/candidate/add")
    public BaseResponse<Object> addChatCandidate(@RequestAttribute("UserId") long userId) throws Exception {
        chatCandidateInteractor.add(userId);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("chat/candidate/remove")
    public BaseResponse<Object> removeChatCandidate(@RequestAttribute("UserId") long userId) throws Exception {
        chatCandidateInteractor.remove(userId);
        return SuccessResponse.getSimpleSuccessResponse();
    }
}