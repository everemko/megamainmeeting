package com.megamainmeeting.db.mapper;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.entity.chat.ChatMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class ChatMessageDbMapper {

    @Autowired
    Logger logger;
    @Autowired
    ImageRepository imageRepository;

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
