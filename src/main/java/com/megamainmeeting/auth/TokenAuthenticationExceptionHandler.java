package com.megamainmeeting.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.spring.base.FailureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationExceptionHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        FailureResponse failureResponse = new FailureResponse(exception.getMessage());
        String message = objectMapper.writeValueAsString(failureResponse);
        response.getWriter().print(message);
    }
}
