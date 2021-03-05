package com.megamainmeeting.entity.chat;


import lombok.*;

@Data
public class NewChatMessage {

    private String message;
    private long userId = -1;
    private long roomId = -1;
}
