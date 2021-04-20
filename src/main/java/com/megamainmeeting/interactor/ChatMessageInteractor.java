package com.megamainmeeting.interactor;

import com.megamainmeeting.db.ChatMessageRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.room.Room;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatMessageInteractor {

    private final MessageChatManager messageChatManager;
    private final ChatMessageRepository chatMessageRepository;
    private final RoomRepositoryJpa roomRepositoryJpa;

    public ChatMessage onNewMessage(NewChatMessage newMessage) throws RoomNotFoundException, UserNotFoundException {
        ChatMessage message = chatMessageRepository.save(newMessage);
        messageChatManager.sendIgnoreSender(message);
        return message;
    }

    public Map<Long, List<ChatMessage>> getMessagesAfterDate(Map<Long, LocalDateTime> map,
                                                             long userId) throws RoomNotFoundException, UserNotInRoomException {
        LinkedHashMap<Long, List<ChatMessage>> newMap = new LinkedHashMap<>();
        for (RoomDb room : roomRepositoryJpa.findAllByUserId(userId)) {
            Optional<LocalDateTime> optional = map.entrySet()
                    .stream()
                    .filter(it -> it.getKey() == room.getId())
                    .map(Map.Entry::getValue)
                    .findFirst();
            List<ChatMessage> list = room.getMessages()
                    .stream()
                    .filter((ChatMessageDb it) -> {
                        if (optional.isEmpty()) return true;
                        else return it.getTime().isAfter(optional.get());
                    })
                    .map(ChatMessageDb::toDomain)
                    .collect(Collectors.toList());
            newMap.put(room.getId(), list);
        }
        return newMap;
    }
}
