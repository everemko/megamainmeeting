package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.db.mapper.ChatMessageDbMapper;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.db.ChatMessageRepositoryJpa;

import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageRepositoryJpa chatMessageRepositoryJpa;
    @Autowired
    private RoomRepositoryJpa roomRepository;
    @Autowired
    private UserRepositoryJpa userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ChatMessageDbMapper chatMessageDbMapper;


    @Override
    public ChatMessage save(NewChatMessage message) throws RoomNotFoundException, UserNotFoundException, IOException {
        UserDb userDb = userRepository.findById(message.getUserId()).orElseThrow(UserNotFoundException::new);
        RoomDb roomDb = roomRepository.findById(message.getRoomId()).orElseThrow(RoomNotFoundException::new);
        ChatMessageDb chatMessageDb = new ChatMessageDb();
        chatMessageDb.setUser(userDb);
        chatMessageDb.setRoom(roomDb);
        chatMessageDb.setMessage(message.getMessage());
        if (message.isImage()) {
            String imageUrl = imageRepository.saveImage(message.getImage());
            chatMessageDb.setImageUrl(imageUrl);
        }
        ChatMessageDb savedMessage = chatMessageRepositoryJpa.save(chatMessageDb);
        return chatMessageDbMapper.map(savedMessage);
    }

    @Override
    public ChatMessage get(long messageId) throws ChatMessageNotFoundException {
        ChatMessageDb chatMessageDb = chatMessageRepositoryJpa.findById(messageId)
                .orElseThrow(ChatMessageNotFoundException::new);
        return chatMessageDbMapper.map(chatMessageDb);
    }

    @Override
    public void update(ChatMessage chatMessage) throws ChatMessageNotFoundException {
        ChatMessageDb chatMessageDb = chatMessageRepositoryJpa.findById(chatMessage.getId()).orElseThrow(ChatMessageNotFoundException::new);
        chatMessageDb.setMessage(chatMessage.getMessage());
        chatMessageDb.setRead(chatMessage.isRead());
        chatMessageRepositoryJpa.save(chatMessageDb);
    }
}
