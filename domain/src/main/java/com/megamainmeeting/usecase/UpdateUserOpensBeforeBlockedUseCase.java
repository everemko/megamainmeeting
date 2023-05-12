package com.megamainmeeting.usecase;

import javax.inject.Inject;

public class UpdateUserOpensBeforeBlockedUseCase {

    @Inject
    UpdateUserOpensBeforeBlockedUseCase userOpensBeforeBlockedUseCase;

    public void invoke(){
        userOpensBeforeBlockedUseCase.invoke();
    }
}
