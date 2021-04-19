package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserChatCandidateQueueImpl implements UserChatCandidateQueue {

    private final Set<ChatCandidate> users = new HashSet<>();
    private final RoomRepository roomRepository;

    @Override
    public void add(ChatCandidate chatCandidate){
        users.add(chatCandidate);
    }

    @Override
    synchronized public ChatCandidate findMatch(ChatCandidate chatCandidate) {
        ChatCandidate match = users.stream()
                .filter(candidate -> candidate.isMatch(chatCandidate))
                .findFirst()
                .orElse(null);
        if(match != null) users.remove(match);
        return match;
    }

    @Override
    public boolean isExist(long userId) {
        return users.stream().anyMatch(it -> it.getUserId() == userId);
    }

    @Override
    public void remove(long userId) {
        users.removeIf(it -> it.getUserId() == userId);
    }

    @Override
    public List<Long> getQueue() {
        return users.stream().map(ChatCandidate::getUserId).collect(Collectors.toList());
    }
}
