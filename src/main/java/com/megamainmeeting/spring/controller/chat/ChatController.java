package com.megamainmeeting.spring.controller.chat;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.spring.base.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @PostMapping("chat/messages/after/date")
    public BaseResponse<Map<Long, List<ChatMessage>>> getMessages(@RequestAttribute("UserId") long userId,
                                                              @RequestBody Map<Long, LocalDateTime> map) throws Exception{
        Map<Long, List<ChatMessage>> messages = chatMessageInteractor.getMessagesAfterDate(map, userId);
        return SuccessResponse.getSuccessInstance(messages);
    }

}