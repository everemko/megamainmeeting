package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Data;

@Data
public class UserOpens {

    private long userId;
    private long openRequestId;
    private UserOpenType type;
    private String userOpensValue;


    public static UserOpens getInstance(long userId,
                                        long openRequestId,
                                        UserOpenType type,
                                        String userOpensValue){
        UserOpens userOpens = new UserOpens();
        userOpens.userId = userId;
        userOpens.openRequestId = openRequestId;
        userOpens.type = type;
        userOpens.userOpensValue = userOpensValue;
        return userOpens;
    }
}
