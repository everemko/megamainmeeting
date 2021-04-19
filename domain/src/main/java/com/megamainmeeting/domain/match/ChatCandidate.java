package com.megamainmeeting.domain.match;

import com.megamainmeeting.domain.error.AddChatCandidateErrorStrings;
import com.megamainmeeting.domain.error.AddChatCandidateException;
import com.megamainmeeting.domain.error.ErrorMessages;
import com.megamainmeeting.entity.room.RoomList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"userId"})
@Data
public class ChatCandidate {

    private long userId = -1;
    private long ageFrom = -1;
    private long ageTo = -1;
    private long age = -1;
    private ChatGoal chatGoal;
    private RoomList roomList;

    public void checkValid() throws AddChatCandidateException {
        if(userId == -1) throw new AddChatCandidateException(AddChatCandidateErrorStrings.NOT_VALID);
        if(ageFrom < 18) throw new AddChatCandidateException(AddChatCandidateErrorStrings.NOT_VALID);
        if(ageTo == -1) throw new AddChatCandidateException(AddChatCandidateErrorStrings.NOT_VALID);
        if(age < 18) throw new AddChatCandidateException(ErrorMessages.INTERNAL_SERVER_ERROR);
        if(chatGoal == null) throw new AddChatCandidateException(AddChatCandidateErrorStrings.NOT_VALID);
    }

    public boolean isMatch(ChatCandidate chatCandidate){
        if(roomList.isUserInRoom(chatCandidate.getUserId())) return false;
        Matcher matcher1 = new Matcher(chatCandidate, this);
        Matcher matcher2 = new Matcher(this, chatCandidate);
        return matcher1.isMatch() && matcher2.isMatch();
    }

    @AllArgsConstructor
    static class Matcher{
        private ChatCandidate chatCandidate1;
        private ChatCandidate chatCandidate2;

        public boolean isMatch(){
            if(chatCandidate1.userId == chatCandidate2.getUserId()) return false;
            if(chatCandidate1.ageFrom > chatCandidate2.age) return false;
            if(chatCandidate1.ageTo < chatCandidate2.age) return false;
            if(chatCandidate1.getChatGoal() != chatCandidate2.chatGoal) return false;
            return true;
        }
    }
}
