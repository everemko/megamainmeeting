package com.megamainmeeting.entity.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class ChatMessage {

    private long id;
    private String message;
    private Room room;
    private long userId;
    private LocalDateTime time;
    private boolean isRead;
}
