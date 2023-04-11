package com.megamainmeeting.spring.controller.chat;

import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.database.repository.ChatMessageRepositoryImpl;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.domain.messaging.ChatMessageInteractor;
import com.megamainmeeting.entity.chat.ChatCandidateRequest;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ChatController {


    private final Logger logger;
    private final ChatMessageInteractor chatMessageInteractor;
    private final UserChatCandidateInteractor chatCandidateInteractor;
    private final RoomRepositoryJpa roomRepositoryJpa;
    private final ChatMessageRepositoryImpl chatMessageRepository;


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
        LinkedHashMap<Long, List<ChatMessage>> newMap = new LinkedHashMap<>();
        for (Map.Entry<Long, LocalDateTime> entry : map.entrySet()) {
            RoomDb room = roomRepositoryJpa.findById(entry.getKey()).orElseThrow(RoomNotFoundException::new);
            if (!room.isUserInRoom(userId)) {
                throw new UserNotInRoomException();
            }
            List<ChatMessage> list = room.getMessages()
                    .stream()
                    .filter(it -> it.getTime().isAfter(entry.getValue()))
                    .map(chatMessageRepository::map)
                    .collect(Collectors.toList());
            newMap.put(entry.getKey(), list);
        }
        return SuccessResponse.getSuccessInstance(newMap);
    }

}