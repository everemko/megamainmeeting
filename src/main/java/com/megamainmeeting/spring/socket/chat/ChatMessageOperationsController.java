package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.entity.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageOperationsController {

    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    UserNotifier userNotifier;

    public Object handle(ReadMessageOperationDto readMessageOperationDto, long userId) throws ChatMessageNotFoundException,
            UserNotInRoomException, RoomNotFoundException {
        ChatMessage chatMessage = chatMessageRepository.get(readMessageOperationDto.getMessageId());
        if (!chatMessage.getRoom().isUserInRoom(userId)) throw new UserNotInRoomException();
        chatMessage.setRead(true);
        chatMessageRepository.update(chatMessage);
        userNotifier.notifyChatMessageUpdated(chatMessage);
        return null;
    }
}
