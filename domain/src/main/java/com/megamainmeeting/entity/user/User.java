package com.megamainmeeting.entity.user;

import com.megamainmeeting.entity.room.RoomList;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    private long id;

    private long age;


    private RoomList roomList;
}
