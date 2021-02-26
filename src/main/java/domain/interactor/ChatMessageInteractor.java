package domain.interactor;

import domain.ChatMessageRepository;
import domain.RoomRepository;
import domain.entity.chat.ChatMessage;
import domain.entity.chat.NewChatMessage;
import domain.entity.chat.Room;
import lombok.AllArgsConstructor;
import spring.controller.chat.MessageChatManager;

@AllArgsConstructor
public class ChatMessageInteractor {

    private final MessageChatManager messageChatManager;
    private final ChatMessageRepository chatMessageRepository;

    public void onNewMessage(NewChatMessage newMessage){
        ChatMessage message = chatMessageRepository.save(newMessage);
        messageChatManager.sendIgnoreSender(message);
    }


}
