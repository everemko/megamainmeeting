package spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegistrationResult {

    private String token;
    private long userId;
}
