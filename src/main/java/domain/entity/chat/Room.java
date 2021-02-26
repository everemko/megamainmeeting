package domain.entity.chat;

import domain.entity.user.User;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Room {

    private long id;
    private Set<Long> users = new HashSet<>();

    public void addUser(long userId){
        users.add(userId);
    }
}

