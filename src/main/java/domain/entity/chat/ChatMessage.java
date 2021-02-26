package domain.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
public class ChatMessage {

    private long id;
    private String text;
    private Room room;
    private long userId;
}
