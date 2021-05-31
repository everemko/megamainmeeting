package com.megamainmeeting.spring.controller.chat;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.spring.base.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public BaseResponse<ChatMessage> processMessage(@RequestAttribute("UserId") long userId,
                                          @RequestPart("roomId") long roomId,
                                          @RequestPart(value = "message", required = false) String message,
                                          @RequestPart(value = "image", required = false) MultipartFile file) throws Exception {
        NewChatMessage newChatMessage = NewChatMessage.getInstance(
                message,
                file != null ? file.getBytes() : null,
                userId,
                roomId
        );
        ChatMessage chatMessage = chatMessageInteractor.onNewMessage(newChatMessage);
        return BaseResponse.getSuccessInstance(chatMessage);
    }

    @PostMapping("chat/candidate/add")
    public BaseResponse<Object> addChatCandidate(@RequestAttribute("UserId") long userId,
                                                 @RequestBody ChatCandidateRequest chatCandidateRequest) throws Exception {

        chatCandidateInteractor.add(userId, chatCandidateRequest);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("chat/candidate/remove")
    public BaseResponse<Object> removeChatCandidate(@RequestAttribute("UserId") long userId) throws Exception {
        chatCandidateInteractor.remove(userId);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("chat/messages/after/date")
    public BaseResponse<Map<Long, List<ChatMessage>>> getMessages(@RequestAttribute("UserId") long userId,
                                                                  @RequestBody Map<Long, LocalDateTime> map) throws Exception {
        Map<Long, List<ChatMessage>> messages = chatMessageInteractor.getMessagesAfterDate(map, userId);
        return SuccessResponse.getSuccessInstance(messages);
    }

}