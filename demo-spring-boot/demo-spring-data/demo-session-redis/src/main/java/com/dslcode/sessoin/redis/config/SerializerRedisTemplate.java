package com.dslcode.sessoin.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongsilin on 2017/3/8.
 */
@Slf4j
@Component
public class SerializerRedisTemplate<T>{

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisValueSerializer redisValueSerializer;

    private RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
    public static final int TIMEOUT_MINUTES = 30;

    @PostConstruct
    public void init(){
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.afterPropertiesSet();
    }


    public void set(String key, T t) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.set.key is null");
        this.redisTemplate.opsForValue().set(key, t, TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }

    public T get(String key) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.get.key is null");
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.delete.key is null");
        redisTemplate.delete(key);
    }

    public void updateExpire(String key) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.updateExpire.key is null");
        redisTemplate.expire(key, TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }


}