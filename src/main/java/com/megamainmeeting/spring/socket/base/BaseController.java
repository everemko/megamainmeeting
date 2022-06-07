package com.megamainmeeting.spring.socket.base;

public interface BaseController<T, R> {

    public R handle(T dto, long userId) throws Exception;
}
