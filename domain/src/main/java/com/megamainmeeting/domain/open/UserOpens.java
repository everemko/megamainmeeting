package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Data;

@Data
public class UserOpens {

    private long userId;
    private long openRequestId;
    private UserOpenType type;

}
