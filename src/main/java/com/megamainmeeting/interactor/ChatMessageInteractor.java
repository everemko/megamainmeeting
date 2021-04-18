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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
                                                               long userId) throws RoomNotFoundException, UserNotInRoomException{
        LinkedHashMap<Long, List<ChatMessage>> newMap = new LinkedHashMap<>();
        for(Map.Entry<Long, LocalDateTime> entry: map.entrySet()){
            RoomDb room = roomRepositoryJpa.findById(entry.getKey()).orElseThrow(RoomNotFoundException::new);
            if(!room.isUserInRoom(userId)){
                throw new UserNotInRoomException();
            }
            List<ChatMessage> list = room.getMessages()
                    .stream()
                    .filter(it -> it.getTime().isAfter(entry.getValue()))
                    .map(ChatMessageDb::toDomain)
                    .collect(Collectors.toList());
            newMap.put(entry.getKey(), list);
        };
        return newMap;
    }
}
