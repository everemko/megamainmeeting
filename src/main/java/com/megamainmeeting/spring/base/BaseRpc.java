package com.megamainmeeting.spring.base;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
abstract public class BaseRpc {

    private String method;

}
