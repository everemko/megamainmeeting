package com.megamainmeeting.domain.open;

import lombok.Data;

import java.util.Set;

@Data
public class UserOpensSet {

    private long userId;
    private long openRequestId;
    private Set<UserOpenType> openTypes;



    public int getCount(){
        return openTypes.size();
    }
}
