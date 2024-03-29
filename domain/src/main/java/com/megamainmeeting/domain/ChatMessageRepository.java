package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;

public interface ChatMessageRepository {

    public ChatMessage save(NewChatMessage message) throws RoomNotFoundException, UserNotFoundException;

    ChatMessage get(long messageId) throws ChatMessageNotFoundException;

    void update(ChatMessage message);
}
