package com.megamainmeeting.entity.chat;

import com.megamainmeeting.domain.match.ChatGoal;
import lombok.Data;

//goal
@Data
public class ChatCandidateRequest {

    private long ageFrom;
    private long ageTo;

    // 0 - Flirt
    // 1 - Chat
    private long goal;

    public ChatGoal getChatGoal(){
        if(goal == 0) return ChatGoal.Flirt;
        if(goal == 1) return ChatGoal.Chat;
        return null;
    }
}
