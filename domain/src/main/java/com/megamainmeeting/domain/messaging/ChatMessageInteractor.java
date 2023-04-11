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

import javax.inject.Inject;
import java.io.IOException;

public class ChatMessageInteractor {

    @Inject
    private MessageChatManager messageChatManager;
    @Inject
    private ChatMessageRepository chatMessageRepository;
    @Inject
    private UserOpeningCheck userOpeningCheck;
    @Inject
    private UserRepository userRepositoryJpa;
    @Inject
    private UserMessagePushService messagePushService;


    synchronized
    public ChatMessage onNewMessage(NewChatMessage newMessage) throws RoomNotFoundException, UserNotFoundException,
            RoomIsBlockedException, UserNotInRoomException, OpenRequestNotFoundException, BadDataException, IOException {
        newMessage.checkValid();
        userOpeningCheck.checkBeforeMessage(newMessage.getRoomId(), newMessage.getUserId());
        ChatMessage message = chatMessageRepository.save(newMessage);
        User sender = userRepositoryJpa.getById(message.getId());
        messageChatManager.sendIgnoreSender(message);
        messagePushService.sendMessage(message, sender);
        userOpeningCheck.checkAfterMessage(newMessage.getRoomId());
        return message;
    }
}
