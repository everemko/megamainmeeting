package com.megamainmeeting.domain.open;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OpenRequest {

    private long id;
    private long roomId;
    private LocalDateTime time;
    private Set<UserOpens> userOpens;

    public boolean isAllOpens(long users){
        return userOpens.size() == users;
    }

    public List<UserOpens> getByUserId(long userId){
        return userOpens.stream().filter(it -> it.getUserId() == userId).collect(Collectors.toList());
    }

    public static OpenRequest getInstance(long openRequestId, long roomId, Set<UserOpens> userOpens, LocalDateTime time){
        OpenRequest openRequest = new OpenRequest();
        openRequest.setId(openRequestId);
        openRequest.setRoomId(roomId);
        openRequest.setUserOpens(userOpens);
        openRequest.setTime(time);
        return openRequest;
    }
}
