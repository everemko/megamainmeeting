package com.megamainmeeting.entity.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    @Getter
    @Setter
    private long id;
}
