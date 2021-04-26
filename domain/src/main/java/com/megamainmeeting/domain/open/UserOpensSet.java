package com.megamainmeeting.domain.open;

import lombok.Data;

import java.util.Set;

@Data
public class UserOpensSet {

    private long userId;
    private long roomId;
    private Set<UserOpenType> openTypes;

    public static UserOpensSet getInstanceAvailable(User user, long roomId){
        UserOpensSet userOpensSet = new UserOpensSet();
        userOpensSet.setRoomId(roomId);
        userOpensSet.setUserId(user.getId());
        userOpensSet.setOpenTypes(user.getAvailable());
        return userOpensSet;
    }

    public static UserOpensSet getInstanceUsed(User user, long roomId){
        UserOpensSet userOpensSet = new UserOpensSet();
        userOpensSet.setRoomId(roomId);
        userOpensSet.setUserId(user.getId());
        userOpensSet.setOpenTypes(user.getOpensUsed());
        return userOpensSet;
    }

    public int getCount(){
        return openTypes.size();
    }
}
