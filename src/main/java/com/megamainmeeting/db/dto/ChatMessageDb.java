package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.chat.ChatMessage;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@Table(name = "chat_messages")
@Entity
public class ChatMessageDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomDb room;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDb user;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
    private boolean isRead = false;

    public ChatMessage toDomain(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setMessage(message);
        chatMessage.setRoom(room.toDomain());
        chatMessage.setUserId(user.getId());
        chatMessage.setTime(time);
        chatMessage.setRead(isRead);
        return chatMessage;
    }

    public static ChatMessageDb getInstance(ChatMessage message){
        RoomDb roomDb = new RoomDb();
        UserDb userDb = new UserDb();
        userDb.setId(message.getUserId());
        roomDb.setId(message.getRoom().getId());
        ChatMessageDb chatMessageDb = new ChatMessageDb();
        chatMessageDb.setId(message.getId());
        chatMessageDb.setMessage(message.getMessage());
        chatMessageDb.setRoom(roomDb);
        chatMessageDb.setRead(message.isRead());
        chatMessageDb.setTime(message.getTime());
        chatMessageDb.setUser(userDb);
        return chatMessageDb;
    }
}
