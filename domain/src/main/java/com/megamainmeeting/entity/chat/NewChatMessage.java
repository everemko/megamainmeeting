package com.megamainmeeting.entity.chat;


import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.error.BadDataException;
import lombok.*;

import java.io.ByteArrayInputStream;

@Data
public class NewChatMessage {

    private String message;
    private byte[] image;
    private long userId = -1;
    private long roomId = -1;

    public void checkValid() throws BadDataException {
        if (userId == -1) throw new BadDataException();
        if (roomId == -1) throw new BadDataException();
    }

    public boolean isImage(){
        return image != null;
    }

    public static NewChatMessage getInstance(
            String message,
            byte[] image,
            long userId,
            long roomId
    ) {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.message = message;
        newChatMessage.image = image;
        newChatMessage.userId = userId;
        newChatMessage.roomId = roomId;
        return newChatMessage;
    }
}

