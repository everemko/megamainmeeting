package com.megamainmeeting.spring.utils;

import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.error.TokenNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//public class AuthenticationRestHeaderInterceptor
//        implements AsyncHandlerInterceptor {
//
//    private static final String HEADER_TOKEN = "Token";
//    private static final String HEADER_USER_ID = "UserId";
//    @Autowired
//    private SessionRepository sessionRepository;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader(HEADER_TOKEN);
//        if(token == null) throw new TokenNotFoundException();
//        long userId = sessionRepository.getUserId(token);
//        request.setAttribute(HEADER_USER_ID, userId);
//        return true;
//    }
//}
