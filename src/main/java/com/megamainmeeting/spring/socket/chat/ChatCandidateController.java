package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.dto.ReadyStatusDto;
import com.megamainmeeting.error.WebSocketSessionNotFoundException;
import com.megamainmeeting.spring.SocketSessions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@AllArgsConstructor
public class ChatCandidateController {

    private final UserChatCandidateInteractor userChatCandidateInteractor;
    private final SocketSessions sessions;

    public void userStatus(ReadyStatusDto dto, WebSocketSession session) throws UserNotFoundException,
            UserNotMatchException, WebSocketSessionNotFoundException {
        if (dto.isReady()) {
            userChatCandidateInteractor.setUserReady(sessions.getUserId(session));
        } else {
            userChatCandidateInteractor.setUserNotReady(sessions.getUserId(session));
        }
    }
}
