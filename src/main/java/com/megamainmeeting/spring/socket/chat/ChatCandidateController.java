package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.domain.error.UserNotChatMatchException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
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
            UserNotChatMatchException, WebSocketSessionNotFoundException {
        if (dto.isReady()) {
            userChatCandidateInteractor.setUserReady(sessions.getUserId(session));
        } else {
            userChatCandidateInteractor.setUserNotReady(sessions.getUserId(session));
        }
    }
}
