package com.zfoo.orm.service;

/**
 * spring-orm，由于每种orm框架都有自己的实现方式，Spring希望以统一的方式整合底层的持久化技术，事务管理。
 * <p>
 * 但是为了这样做就会造成对框架的过度封装，有利就有弊，可以参加Why I hate framework，创建工厂的工厂
 * </p>
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 17:38
 */
public interface IOrmService {

    void scheduleWithNoDelay(Runnable runnable);

    void schedule(Runnable runnable, long delay);

    void scheduleAtFixedRate(Runnable runnable, long period);

}
