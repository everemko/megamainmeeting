package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.ChatMessageRepositoryJpa;
import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.database.dto.ChatMessageDb;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    @Inject
    private Logger logger;
    @Inject
    private ChatMessageRepositoryJpa chatMessageRepositoryJpa;
    @Inject
    private RoomRepositoryJpa roomRepository;
    @Inject
    private UserRepositoryJpa userRepository;
    @Inject
    private ImageRepository imageRepository;


    @Override
    public ChatMessage save(NewChatMessage message) throws RoomNotFoundException, UserNotFoundException, IOException {
        UserDb userDb = userRepository.findById(message.getUserId()).orElseThrow(UserNotFoundException::new);
        RoomDb roomDb = roomRepository.findById(message.getRoomId()).orElseThrow(RoomNotFoundException::new);
        ChatMessageDb chatMessageDb = new ChatMessageDb();
        chatMessageDb.setUser(userDb);
        chatMessageDb.setRoom(roomDb);
        chatMessageDb.setMessage(message.getMessage());
        if (message.isImage()) {
            long imageId = imageRepository.saveImage(message.getImage());
            chatMessageDb.setImageId(imageId);
        }
        ChatMessageDb savedMessage = chatMessageRepositoryJpa.save(chatMessageDb);
        return map(savedMessage);
    }

    @Override
    public ChatMessage get(long messageId) throws ChatMessageNotFoundException {
        ChatMessageDb chatMessageDb = chatMessageRepositoryJpa.findById(messageId)
                .orElseThrow(ChatMessageNotFoundException::new);
        return map(chatMessageDb);
    }

    @Override
    public void update(ChatMessage chatMessage) throws ChatMessageNotFoundException {
        ChatMessageDb chatMessageDb = chatMessageRepositoryJpa.findById(chatMessage.getId()).orElseThrow(ChatMessageNotFoundException::new);
        chatMessageDb.setMessage(chatMessage.getMessage());
        chatMessageDb.setRead(chatMessage.isRead());
        chatMessageRepositoryJpa.save(chatMessageDb);
    }

    public ChatMessage map(ChatMessageDb chatMessageDb){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageDb.getId());
        chatMessage.setMessage(chatMessageDb.getMessage());
        chatMessage.setRoom(chatMessageDb.getRoom().toDomain());
        chatMessage.setUserId(chatMessageDb.getUser().getId());
        chatMessage.setTime(chatMessageDb.getTime());
        chatMessage.setRead(chatMessageDb.isRead());
        if(chatMessageDb.isImage()) {
            try {
                String url = imageRepository.getDownloadLink(chatMessageDb.getImageId());
                chatMessage.setImageUrl(url);
            } catch (FileNotFoundException exception){
                logger.error(this.getClass().getSimpleName(), exception);
            }
        }
        return chatMessage;
    }
}
