package com.megamainmeeting.config;


import com.megamainmeeting.spring.controller.queue.UserChatQueueController;
import com.megamainmeeting.spring.controller.registration.RegistrationController;
//import com.megamainmeeting.spring.utils.AuthenticationRestHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationRestHeaderInterceptor)
//                .excludePathPatterns(AUTHENTICATION_IGNORE);
//    }

    private final static String[] AUTHENTICATION_IGNORE = {
            RegistrationController.REGISTER_ANONYMOUS_PATH, UserChatQueueController.QUEUE
    };
}
