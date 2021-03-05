package com.megamainmeeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.spring.base.BaseRpc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opentest4j.TestAbortedException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestWebSocketSession implements WebSocketSession {


    private List<String> messages = new ArrayList<>();
    private List<MessageTest<Object>> tests = new ArrayList<>();

    @Override
    public String getId() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public String getAcceptedProtocol() {
        return null;
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {

    }

    @Override
    public int getTextMessageSizeLimit() {
        return 0;
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {

    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return 0;
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return null;
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException, JsonProcessingException {
        TextMessage textMessage = (TextMessage) message;
        messages.add(textMessage.getPayload());
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void close(CloseStatus status) throws IOException {

    }

    public void addTest(MessageTest<Object> messageTest){
        tests.add(messageTest);
    }

    public void testAll() throws JsonProcessingException{
        for (int i = 0; i < tests.size(); i++){
            tests.get(i).run(messages.get(i));
        }
    }

    public void test(MessageTest<?> test) {
            test.run(messages.remove(0));
    }

    @Getter
    public static class MessageTest<T>{
        private final TestRule<T> rule;
        private final Class<T> clazz;

        public MessageTest(TestRule<T> rule, Class<T> clazz) {
            this.rule = rule;
            this.clazz = clazz;
        }

        void run(String message) {
            try{
                T object = mapper.readValue(message, clazz);
                rule.test(object);
            } catch (JsonProcessingException e){
                throw new TestAbortedException();
            }
        }

        private static final ObjectMapper mapper = new ObjectMapper();
    }

    interface TestRule<T>{
        void test(T object);
    }
}
