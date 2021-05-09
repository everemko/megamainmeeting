package com.megamainmeeting.db.mapper;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.entity.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageDbMapper {

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
        if(chatMessageDb.getImageUrl() != null) {
            String url = imageRepository.getDownloadLink(chatMessageDb.getImageUrl());
            chatMessage.setImage(url);
        }
        return chatMessage;
    }
}
