package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
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

    private final Set<User> users = new HashSet<>();
    private final RoomRepository roomRepository;

    @Override
    public void add(User user){
        users.add(user);
    }

    @Override
    synchronized public User findMatch(long userId) {
        RoomList rooms = roomRepository.getList(userId);
        User userMatch = users.stream()
                .filter(user1 -> !rooms.isUserInRoom(user1.getId()))
                .findFirst()
                .orElse(null);
        if(userMatch != null) users.remove(userMatch);
        return userMatch;
    }

    @Override
    public boolean isExist(User user) {
        return users.contains(user);
    }

    @Override
    public void remove(long userId) {
        User user = new User();
        user.setId(userId);
        users.remove(user);
    }

    @Override
    public List<Long> getQueue() {
        return users.stream().map(User::getId).collect(Collectors.toList());
    }
}
