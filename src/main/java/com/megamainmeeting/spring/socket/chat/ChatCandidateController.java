package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.dto.ReadyStatusDto;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.SocketSessions;
import com.megamainmeeting.spring.socket.base.BaseController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@AllArgsConstructor
public class ChatCandidateController implements BaseController<ReadyStatusDto, Object> {

    private final UserChatCandidateInteractor userChatCandidateInteractor;
    private final SocketSessions sessions;


    @Override
    public Object handle(ReadyStatusDto dto, long userId) throws Exception {
        if (dto.isReady()) {
            userChatCandidateInteractor.setUserReady(userId);
        } else {
            userChatCandidateInteractor.setUserNotReady(userId);
        }
        return new Object();
    }
}
