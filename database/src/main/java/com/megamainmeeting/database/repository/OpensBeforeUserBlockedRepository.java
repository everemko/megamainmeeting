package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.OneValueDbRepositoryJpa;
import com.megamainmeeting.database.dto.OneValueDb;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpensBeforeUserBlockedRepository {

    public static final String OPENS_BEFORE_USER_BLOCKED = "OPENS_BEFORE_USER_BLOCKED";
    @Autowired
    Logger logger;
    @Autowired
    OneValueDbRepositoryJpa oneValueDbJpaRepository;

    public Long getValue() throws IllegalStateException {
        OneValueDb valueDb = oneValueDbJpaRepository.findByName(OPENS_BEFORE_USER_BLOCKED).orElseThrow(IllegalStateException::new);
        try {
            return Long.valueOf(valueDb.getValue());
        } catch (NumberFormatException e) {
            logger.error(OpensBeforeUserBlockedRepository.class.getSimpleName() + "can not cast database value to Long");
        }
        throw new IllegalStateException();
    }

    public void setValue(long value) {
        OneValueDb valueDb = oneValueDbJpaRepository.findByName(OPENS_BEFORE_USER_BLOCKED).orElseGet(this::createNew);
        valueDb.setValue(Long.toString(value));
        oneValueDbJpaRepository.save(valueDb);
    }

    private OneValueDb createNew(){
        OneValueDb oneValueDb = new OneValueDb();
        oneValueDb.setName(OPENS_BEFORE_USER_BLOCKED);
        return oneValueDb;
    }
}
