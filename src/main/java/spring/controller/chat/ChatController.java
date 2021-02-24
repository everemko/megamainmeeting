package spring.controller.chat;

import domain.entity.chat.Message;
import domain.entity.chat.NewMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.base.BaseResponse;

@RestController
@AllArgsConstructor
public class ChatController {

    private final MessageChatManager messageChatManager;
    private final Logger logger;


    @PostMapping("/chat/{room}")
    public BaseResponse<Void> processMessage(@PathVariable long room,
                                       @Payload NewMessage newMessage) {
        logger.info(newMessage.toString());
        messageChatManager.sendMessage(new Message(newMessage.getText()));
        return BaseResponse.getSimpleSuccessResponse();
    }
}