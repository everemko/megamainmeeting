package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USERS")
public class UserDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "date_birth")
    private LocalDateTime dateBirth;
    @Column(nullable = false)
    private Long gender;
    @Column(nullable = false, name = "gender_match")
    private Long genderMatch;

    public User toDomain(){
        return new User(id);
    }

    public static UserDb getInstance(User user){
        UserDb dto = new UserDb();
        dto.id = user.getId();
        return dto;
    }
}
