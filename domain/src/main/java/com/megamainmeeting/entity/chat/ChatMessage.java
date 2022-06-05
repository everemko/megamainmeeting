package com.megamainmeeting.entity.chat;

import com.megamainmeeting.entity.room.Room;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class ChatMessage {

    private long id;
    private String message;
    private Room room;
    private long userId;
    private LocalDateTime time;
    private boolean isRead;
    private String imageUrl;

    public Set<Long> getUsersWithoutSender(){
        return room.getUsers()
                .stream()
                .filter(it -> it != userId)
                .collect(Collectors.toSet());
    }

    public boolean isHasImage(){
        return imageUrl != null;
    }

    public boolean isHasTextMessage(){
        return message != null && !message.isEmpty();
    }
}
