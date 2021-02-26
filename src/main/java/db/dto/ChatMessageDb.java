package db.dto;

import domain.entity.chat.ChatMessage;
import lombok.Data;

import javax.persistence.*;

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

    public ChatMessage toDomain(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setRoom(room.toDomain());
        chatMessage.setUserId(user.getId());
        return chatMessage;
    }
}
