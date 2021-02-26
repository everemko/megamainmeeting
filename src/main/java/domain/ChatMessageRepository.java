package domain;

import domain.entity.chat.ChatMessage;
import domain.entity.chat.NewChatMessage;

public interface ChatMessageRepository {

    public ChatMessage save(NewChatMessage message);
}
