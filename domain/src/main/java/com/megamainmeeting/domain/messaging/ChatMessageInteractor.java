package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.open.UserOpeningCheck;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class ChatMessageInteractor {

    private final MessageChatManager messageChatManager;
    private final ChatMessageRepository chatMessageRepository;

    private final UserOpeningCheck userOpeningCheck;
    private final UserRepository userRepositoryJpa;
    private final UserMessagePushService messagePushService;


    synchronized
    public ChatMessage onNewMessage(NewChatMessage newMessage) throws RoomNotFoundException, UserNotFoundException,
            RoomIsBlockedException, UserNotInRoomException, OpenRequestNotFoundException, BadDataException, IOException {
        newMessage.checkValid();
        userOpeningCheck.checkBeforeMessage(newMessage.getRoomId(), newMessage.getUserId());
        ChatMessage message = chatMessageRepository.save(newMessage);
        User sender = userRepositoryJpa.get(message.getId());
        messageChatManager.sendIgnoreSender(message);
        messagePushService.sendMessage(message, sender);
        userOpeningCheck.checkAfterMessage(newMessage.getRoomId());
        return message;
    }
}
