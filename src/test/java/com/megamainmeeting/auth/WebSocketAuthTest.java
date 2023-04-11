package com.megamainmeeting.auth;

import com.megamainmeeting.Application;
import com.megamainmeeting.domain.error.AuthorizationException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.spring.dto.AuthenticationSocketDto;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.WebSocketSession;

import static com.megamainmeeting.auth.AuthTestContext.TOKEN;
import static com.megamainmeeting.auth.AuthTestContext.USER_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AuthAppConfigTest.class})
public class WebSocketAuthTest {

//    @Test()
//    public void testPositive() throws UserNotFoundException, AuthorizationException {
//        WebSocketSession session = new EmptyWebSocketSession();
//        AuthenticationSocketDto dto = new AuthenticationSocketDto();
//        dto.setUserId(USER_ID);
//        dto.setToken(TOKEN);
//        authenticationController.auth(dto, session);
//        authenticationController.checkAuthorization(session);
//    }
//
//    @Test(expected = AuthorizationException.class)
//    public void testAuthBySession() throws AuthorizationException {
//        WebSocketSession session = new EmptyWebSocketSession();
//        authenticationController.checkAuthorization(session);
//    }
//
//    @Test(expected = AuthorizationException.class)
//    public void testBadToken() throws UserNotFoundException, AuthorizationException {
//        WebSocketSession session = new EmptyWebSocketSession();
//        AuthenticationSocketDto dto = new AuthenticationSocketDto();
//        dto.setUserId(USER_ID);
//        dto.setToken("");
//        authenticationController.auth(dto, session);
//        authenticationController.checkAuthorization(session);
//    }
//
//    @Test(expected = UserNotFoundException.class)
//    public void testUserNotFound() throws UserNotFoundException, AuthorizationException {
//        WebSocketSession session = new EmptyWebSocketSession();
//        AuthenticationSocketDto dto = new AuthenticationSocketDto();
//        dto.setUserId(-1);
//        dto.setToken("");
//        authenticationController.auth(dto, session);
//        authenticationController.checkAuthorization(session);
//    }
}
