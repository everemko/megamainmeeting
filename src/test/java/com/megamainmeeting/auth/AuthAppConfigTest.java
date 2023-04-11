package com.megamainmeeting.auth;

import com.megamainmeeting.config.AppConfig;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.Authentication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static com.megamainmeeting.auth.AuthTestContext.TOKEN;
import static com.megamainmeeting.auth.AuthTestContext.USER_ID;

@TestConfiguration
class AuthAppConfigTest extends AppConfig {

    @Bean()
    @Primary
    public AuthenticationInteractor provideAuthenticationInteractor(){
        return new AuthenticationInteractorImpl();
    }


    static class AuthenticationInteractorImpl implements AuthenticationInteractor{

        @Override
        public boolean isAuth(Authentication auth) throws UserNotFoundException {
            return auth.getUserId() == USER_ID && auth.getToken().equals(TOKEN);
        }
    }


}
