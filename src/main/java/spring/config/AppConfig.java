package spring.config;



import domain.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import db.SessionRepositoryJpa;
import db.UserRepositoryJpa;
import db.repository.RegistrationRepositoryImpl;

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
}
