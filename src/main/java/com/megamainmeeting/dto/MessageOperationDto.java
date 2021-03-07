package com.megamainmeeting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageOperationDto {

    private long messageId;
    private Type type;

    public enum Type {
        READ
    }
}
