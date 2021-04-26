package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class User {

    private long id;
    private Set<UserOpenType> opensUsed;
    private Set<UserOpenType> opens;

    public Set<UserOpenType> getAvailable() {
        return opens.stream()
                .filter(it -> opensUsed.stream().noneMatch(used -> used == it))
                .collect(Collectors.toSet());
    }

    public boolean isOpen(long level){
        return opensUsed.size() >= level;
    }

    public void addOpens(UserOpenType type){
        opensUsed.add(type);
    }
}
