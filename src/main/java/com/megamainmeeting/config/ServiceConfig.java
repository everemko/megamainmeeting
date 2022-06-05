package com.megamainmeeting.config;

import com.megamainmeeting.push.FirebaseClient;
import com.megamainmeeting.push.UserPushTokenRepository;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FirebaseClient provideFirebaseClient(Logger logger, UserPushTokenRepository userPushTokenRepository){
        return new FirebaseClient(logger, userPushTokenRepository);
    }
}
