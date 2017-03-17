package com.dslcode.sessoin.redis.config;

import com.dslcode.core.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongsilin on 2017/3/17.
 */
@Component
public class JsonRedisTemplate<T> {

    public static final int TIMEOUT_MINUTES = 30;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, T t) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.set.key is null");
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJson(t), TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }

    public T get(String key, Class<T> c) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.get.key is null");
        return JsonUtil.readJson(stringRedisTemplate.opsForValue().get(key), c);
    }

    public void delete(String key) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.delete.key is null");
        stringRedisTemplate.delete(key);
    }

    public void updateExpire(String key) throws Exception {
        if(StringUtils.isEmpty(key)) throw new Exception("RedisValueJson.updateExpire.key is null");
        stringRedisTemplate.expire(key, TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }

}
