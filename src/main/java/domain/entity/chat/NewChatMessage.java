package domain.entity.chat;


import lombok.*;

@Data
public class NewChatMessage {

    private String text;
    private long userId;
    private long roomId;
}
