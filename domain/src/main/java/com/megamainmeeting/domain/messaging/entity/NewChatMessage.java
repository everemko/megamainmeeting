package com.megamainmeeting.domain.messaging.entity;


import com.megamainmeeting.domain.error.BadDataException;
import lombok.*;

@Data
public class NewChatMessage {

    private String message;
    private byte[] image;
    private long userId = -1;
    private long roomId = -1;

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

