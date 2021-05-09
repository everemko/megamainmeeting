package com.megamainmeeting.entity.chat;

import com.megamainmeeting.entity.room.Room;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ChatMessage {

    private long id;
    private String message;
    private Room room;
    private long userId;
    private LocalDateTime time;
    private boolean isRead;
    private String image;
}
