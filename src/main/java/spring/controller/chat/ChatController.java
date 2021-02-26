package spring.controller.chat;

import domain.entity.chat.NewChatMessage;
import domain.interactor.ChatMessageInteractor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.base.BaseResponse;
import spring.base.SuccessResponse;

@RestController
@AllArgsConstructor
public class ChatController {


    private final Logger logger;
    private final ChatMessageInteractor chatMessageInteractor;


    @PostMapping("/chat/{room}")
    public BaseResponse<Object> processMessage(@PathVariable long room,
                                       @Payload NewChatMessage newMessage) {
        logger.info(newMessage.toString());
        chatMessageInteractor.onNewMessage(newMessage);
        return SuccessResponse.getSimpleSuccessResponse();
    }
}