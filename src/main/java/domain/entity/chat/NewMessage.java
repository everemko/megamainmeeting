package domain.entity.chat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class NewMessage {

    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private long userId;
}
