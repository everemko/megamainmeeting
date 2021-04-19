package com.megamainmeeting.domain.match;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class OnceRunnable implements Runnable{

    private final AtomicBoolean isRun = new AtomicBoolean(false);
    private final Runnable runnable;

    @Override
    public void run() {
        if(!isRun.compareAndSet(false, true)) return;
        runnable.run();
    }
}
