package com.dslcode.sessoin.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by dongsilin on 2017/3/17.
 */
@Slf4j
@Component
public class RedisValueSerializer implements RedisSerializer<Object> {

    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();
    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public Object deserialize(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return null;
        try {
            return deserializer.convert(bytes);
        }catch (Exception e){
            log.error("redis.get.error={}", e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] serialize(Object object) {
        if (null == object) return EMPTY_ARRAY;
        try {
            return serializer.convert(object);
        }catch (Exception e){
            log.error("redis.set.error={}", e.getMessage());
            return EMPTY_ARRAY;
        }
    }

}