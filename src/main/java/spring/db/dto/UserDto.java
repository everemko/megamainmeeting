package spring.db.dto;

import domain.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
public class UserDto {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public User toDomain(){
        return new User(id);
    }

    public static UserDto getInstance(User user){
        UserDto dto = new UserDto();
        dto.id = user.getId();
        return dto;
    }
}
