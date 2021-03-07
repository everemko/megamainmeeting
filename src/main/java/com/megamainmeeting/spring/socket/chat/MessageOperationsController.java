package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.dto.MessageOperationDto;
import com.megamainmeeting.entity.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageOperationsController {

    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    UserNotifier userNotifier;

    public Object handle(MessageOperationDto messageOperationDto, long userId) throws ChatMessageNotFoundException, UserNotInRoomException {
        ChatMessage chatMessage = chatMessageRepository.get(messageOperationDto.getMessageId());
        if (chatMessage.getRoom().isUserInRoom(userId)) throw new UserNotInRoomException();
        if (messageOperationDto.getType() == MessageOperationDto.Type.READ) {
            chatMessage.setRead(true);
            chatMessageRepository.update(chatMessage);
            userNotifier.notifyChatMessageUpdated(chatMessage);
        }
        return null;
    }
}
