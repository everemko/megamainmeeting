package spring.config;



import db.ChatMessageRepositoryJpa;
import db.RoomRepositoryJpa;
import db.repository.*;
import domain.*;
import domain.interactor.ChatMessageInteractor;
import domain.interactor.UserChatCandidateInteractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import db.SessionRepositoryJpa;
import db.UserRepositoryJpa;
import spring.socket.ChatWebSocketHandler;

@Configuration
public class AppConfig {

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public RegistrationRepository provideRegistrationRepository(UserRepositoryJpa userRepositoryJpa,
                                                                SessionRepositoryJpa sessionRepositoryJpa){
        return new RegistrationRepositoryImpl(userRepositoryJpa, sessionRepositoryJpa);
    }

    @Bean
    public UserChatCandidateQueue provideUserChatCandidateQueue(){
        return new UserChatCandidateQueueImpl();
    }

    @Bean
    public RoomRepository provideRoomRepository(RoomRepositoryJpa roomRepositoryJpa){
//        return new RoomRepositoryImpl(roomRepositoryJpa);
        return new RoomRepositoryMemory();
    }

    @Bean
    public UserRepository provideUserRepository(UserRepositoryJpa userRepositoryJpa){
        return new UserRepositoryImpl(userRepositoryJpa);
    }

    @Bean ChatMessageRepository provideChatMessageRepository(ChatMessageRepositoryJpa chatMessageRepositoryJpa,
                                                             RoomRepository roomRepository){
        return new ChatMessageRepositoryImpl(chatMessageRepositoryJpa, roomRepository);
    }

    @Bean
    public UserChatCandidateInteractor provideUserChatCandidateInteractor(UserChatCandidateQueue userMatchQueue,
                                                                          RoomRepository roomRepository,
                                                                          ChatWebSocketHandler chatWebSocketHandler,
                                                                          UserRepository userRepository){
        return new UserChatCandidateInteractor(userMatchQueue, roomRepository, chatWebSocketHandler, userRepository);
    }

    @Bean
    ChatMessageInteractor provideChatMessageInteractor(ChatWebSocketHandler chatWebSocketHandler,
                                                       ChatMessageRepository chatMessageRepository){
        return  new ChatMessageInteractor(chatWebSocketHandler, chatMessageRepository);
    }
}
