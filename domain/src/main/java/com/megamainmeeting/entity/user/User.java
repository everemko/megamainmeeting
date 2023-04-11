package com.megamainmeeting.entity.user;

import com.megamainmeeting.entity.room.RoomList;
import lombok.*;

import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    private long id;
    private Gender gender;
    private String name;
    private LocalDateTime dateBirth;
    private Gender genderMatch;

    public long getAge(){
        LocalDateTime now = LocalDateTime.now();
        return Period.between(dateBirth.toLocalDate(), now.toLocalDate()).getYears();
    }
}
