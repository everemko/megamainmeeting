package com.megamainmeeting.spring.base;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class NotificationRpcResponse<T> extends BaseRpc {

    private T params;
}
