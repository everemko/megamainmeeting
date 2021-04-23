package com.megamainmeeting.entity.user;

import com.megamainmeeting.entity.room.RoomList;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    private long id;
    private long age;
    private long gender;
    private String name;

}
