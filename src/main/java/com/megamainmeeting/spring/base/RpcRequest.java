package com.megamainmeeting.spring.base;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(exclude = "params", callSuper = true)
public class RpcRequest extends BaseRpc {

    private JsonNode params;
    private long id = -1;

}
