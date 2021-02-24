package spring.db.dto;

import domain.entity.auth.Session;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@Data
@Entity
@Table(name = "SESSION")
public class SessionDto {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String token;

    private long userId;

    public Session toDomain() {
        return new Session(
                id,
                token,
                userId
        );
    }

    public static SessionDto getInstance(UserDto userDto) {
        SessionDto dto = new SessionDto();
        dto.token = new RandomString(60).nextString();
        dto.userId = userDto.getId();
        return dto;
    }

    private static class RandomString {

        /**
         * Generate a random string.
         */
        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static final String lower = upper.toLowerCase(Locale.ROOT);

        public static final String digits = "0123456789";

        public static final String alphanum = upper + lower + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        public RandomString(int length, Random random) {
            this(length, random, alphanum);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
        public RandomString(int length) {
            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {
            this(21);
        }
    }
}
