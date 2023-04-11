package com.megamainmeeting.spring.socket.chat;

import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.spring.dto.ReadyStatusDto;
import com.megamainmeeting.spring.socket.base.BaseController;
import com.megamainmeeting.spring.socket.dto.RpcMethods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatCandidateController implements BaseController<ReadyStatusDto, Object> {

    private final UserChatCandidateInteractor userChatCandidateInteractor;

    @Override
    public Object handle(ReadyStatusDto dto, long userId) throws Exception {
        if (dto.isReady()) {
            userChatCandidateInteractor.setUserReady(userId);
        } else {
            userChatCandidateInteractor.setUserNotReady(userId);
        }
        return new Object();
    }

    @Override
    public String getRpcMethod() {
        return RpcMethods.MESSAGE_HAS_BEEN_READ;
    }

    @Override
    public Class<ReadyStatusDto> getDtoClass() {
        return ReadyStatusDto.class;
    }
}
