package com.megamainmeeting.config;

import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.open.RoomBlockingNotifier;
import com.megamainmeeting.domain.open.UserOpeningCheck;
import com.megamainmeeting.domain.open.UserOpensRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserOpensConfig {

    @Bean
    public UserOpeningCheck provideUserOpeningCheck(UserOpensRepository userOpensRepository,
                                                    UserNotifier userNotifier){
        RoomBlockingNotifier roomBlockingNotifier = new RoomBlockingNotifier(userNotifier);
        return new UserOpeningCheck(userOpensRepository, roomBlockingNotifier);
    }
}
