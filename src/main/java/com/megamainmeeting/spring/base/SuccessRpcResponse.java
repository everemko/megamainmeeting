package com.megamainmeeting.spring.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SuccessRpcResponse<T> extends BaseRpc {

    private Long id;
    private T params;
}
