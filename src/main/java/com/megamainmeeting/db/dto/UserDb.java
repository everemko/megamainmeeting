package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USERS")
public class UserDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public User toDomain(){
        return new User(id);
    }

    public static UserDb getInstance(User user){
        UserDb dto = new UserDb();
        dto.id = user.getId();
        return dto;
    }
}
