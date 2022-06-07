package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.spring.socket.base.BaseController;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageOperationsController implements BaseController<ReadMessageOperationDto, Object> {

    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    UserNotifier userNotifier;

    @Override
    public Object handle(ReadMessageOperationDto dto, long userId) throws Exception {
        ChatMessage chatMessage = chatMessageRepository.get(dto.getMessageId());
        if (!chatMessage.getRoom().isUserInRoom(userId)) throw new UserNotInRoomException();
        chatMessage.setRead(true);
        chatMessageRepository.update(chatMessage);
        userNotifier.notifyChatMessageUpdated(chatMessage);
        return null;
    }
}
