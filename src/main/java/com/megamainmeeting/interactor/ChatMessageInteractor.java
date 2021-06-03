package com.megamainmeeting.interactor;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.mapper.ChatMessageDbMapper;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.open.RoomBlockingNotifier;
import com.megamainmeeting.domain.open.UserOpeningCheck;
import com.megamainmeeting.domain.open.UserOpensRepository;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatMessageInteractor {

    private final MessageChatManager messageChatManager;
    private final ChatMessageRepository chatMessageRepository;
    private final RoomRepositoryJpa roomRepositoryJpa;
    private final UserOpeningCheck userOpeningCheck;
    private final UserMessagePushService messagePushService;
    private ChatMessageDbMapper chatMessageDbMapper;

    synchronized
    public ChatMessage onNewMessage(NewChatMessage newMessage) throws RoomNotFoundException, UserNotFoundException,
            RoomIsBlockedException, UserNotInRoomException, OpenRequestNotFoundException, BadDataException, IOException {
        newMessage.checkValid();
        userOpeningCheck.checkBeforeMessage(newMessage.getRoomId(), newMessage.getUserId());
        ChatMessage message = chatMessageRepository.save(newMessage);
        messageChatManager.sendIgnoreSender(message);
        messagePushService.send(message.getUsersWithoutSender(), message.getMessage());
        userOpeningCheck.checkAfterMessage(newMessage.getRoomId());
        return message;
    }

    public Map<Long, List<ChatMessage>> getMessagesAfterDate(Map<Long, LocalDateTime> map,
                                                             long userId) throws RoomNotFoundException, UserNotInRoomException {
        LinkedHashMap<Long, List<ChatMessage>> newMap = new LinkedHashMap<>();
        for(Map.Entry<Long, LocalDateTime> entry: map.entrySet()){
            RoomDb room = roomRepositoryJpa.findById(entry.getKey()).orElseThrow(RoomNotFoundException::new);
            if(!room.isUserInRoom(userId)){
                throw new UserNotInRoomException();
            }
            List<ChatMessage> list = room.getMessages()
                    .stream()
                    .filter(it -> it.getTime().isAfter(entry.getValue()))
                    .map(it -> chatMessageDbMapper.map(it))
                    .collect(Collectors.toList());
            newMap.put(entry.getKey(), list);
        }
        return newMap;
    }
}
