package com.megamainmeeting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.spring.base.FailureResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.megamainmeeting.domain.error.ErrorMessages.SESSION_NOT_FOUND_ERROR;
import static com.megamainmeeting.domain.error.ErrorMessages.TOKEN_NOT_FOUND_ERROR;

class TokenFilter implements Filter {

    private static final String HEADER_TOKEN = "Token";
    private static final String HEADER_USER_ID = "UserId";
    @Autowired
    Logger logger;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest r, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) r;
            HttpServletResponse response = (HttpServletResponse) resp;
            try {

                String token = request.getHeader(HEADER_TOKEN);
                if (token == null) {
                    FailureResponse failureResponse = new FailureResponse(TOKEN_NOT_FOUND_ERROR);
                    String message = objectMapper.writeValueAsString(failureResponse);
                    SecurityContextHolder.clearContext();
                    response.getWriter().print(message);
                }
                long userId = sessionRepository.getUserId(token);
                request.setAttribute(HEADER_USER_ID, userId);
            } catch (SessionNotFoundException e){
                FailureResponse failureResponse = new FailureResponse(SESSION_NOT_FOUND_ERROR);
                String message = objectMapper.writeValueAsString(failureResponse);
                SecurityContextHolder.clearContext();
                response.getWriter().print(message);
            }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
