package com.dslcode.demo.order.traditional.service;

/**
 * Created by dongsilin on 2017/4/8.
 */
public interface RedisDataService {

    void stringPut(String key, String value);

    String stringGet(String key);

    void listPut(String key, String value);

    void setPut(String key, String value);


    String setPop(String key);

    String listGet(String key);

    boolean setHasNext(String key);
}
