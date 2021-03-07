package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.db.ChatMessageRepositoryJpa;

import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageRepositoryJpa chatMessageRepositoryJpa;
    @Autowired
    private RoomRepositoryJpa roomRepository;
    @Autowired
    private UserRepositoryJpa userRepository;


    @Override
    public ChatMessage save(NewChatMessage message) throws RoomNotFoundException, UserNotFoundException {
        UserDb userDb = userRepository.findById(message.getUserId()).orElseThrow(UserNotFoundException::new);
        RoomDb roomDb = roomRepository.findById(message.getRoomId()).orElseThrow(RoomNotFoundException::new);
        ChatMessageDb chatMessageDb = new ChatMessageDb();
        chatMessageDb.setUser(userDb);
        chatMessageDb.setRoom(roomDb);
        chatMessageDb.setMessage(message.getMessage());
        ChatMessageDb savedMessage = chatMessageRepositoryJpa.save(chatMessageDb);
        return savedMessage.toDomain();
    }

    @Override
    public ChatMessage get(long messageId) throws ChatMessageNotFoundException{
        return chatMessageRepositoryJpa.findById(messageId).orElseThrow(ChatMessageNotFoundException::new).toDomain();
    }

    @Override
    public void update(ChatMessage chatMessage) {
        chatMessageRepositoryJpa.save(ChatMessageDb.getInstance(chatMessage));
    }
}
