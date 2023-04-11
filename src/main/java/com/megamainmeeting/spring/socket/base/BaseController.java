package com.megamainmeeting.spring.socket.base;

public interface BaseController<D, R> {

    public R handle(D dto, long userId) throws Exception;

    public String getRpcMethod();

    public Class<D> getDtoClass();
}
