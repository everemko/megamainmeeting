package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.domain.messaging.entity.NewChatMessage;
import com.megamainmeeting.entity.user.User;

import javax.inject.Inject;
import java.io.IOException;

public class NewMessageUseCase {

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
    @Inject
    private UserOpensRepository userOpensRepository;
    @Inject
    private RoomBlockingNotifier roomBlockingNotifier;
    @Inject
    private RoomBlockChecker roomBlockChecker;


    synchronized
    public ChatMessage onNewMessage(NewChatMessage newMessage) throws RoomNotFoundException, UserNotFoundException,
            RoomIsBlockedException, UserNotInRoomException, IOException {
        if (roomBlockChecker.isRoomBlockedForUser(newMessage.getRoomId(), newMessage.getUserId())) {
            throw new RoomIsBlockedException();
        }
        ChatMessage message = chatMessageRepository.save(newMessage);
        messageChatManager.sendIgnoreSender(message);
        messagePushService.sendMessage(message, sender);
        room.incrementMessageCount();
        if(roomBlockChecker.isRoomNeedToBlockForUser(newMessage.getRoomId(), newMessage.getUserId())){
            OpenRequest openRequest = userOpensRepository.blockRoom(newMessage.getRoomId());
            roomBlockingNotifier.notifyRoomShouldOpens(room, openRequest);
        }
        return message;
    }
}
