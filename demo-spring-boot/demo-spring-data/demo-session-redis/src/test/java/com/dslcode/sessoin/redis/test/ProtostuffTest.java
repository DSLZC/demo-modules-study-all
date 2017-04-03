package com.dslcode.sessoin.redis.test;

import com.dslcode.sessoin.redis.DemoSessionRedisApplicationTests;
import com.dslcode.sessoin.redis.pojo.WXMemberInfo;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

/**
 * Created by dongsilin on 2017/3/17.
 * Protostuff序列化测试 ，性能好
 */
@Slf4j
public class ProtostuffTest extends DemoSessionRedisApplicationTests {

    private RuntimeSchema<WXMemberInfo> protostuffSchema = RuntimeSchema.createFrom(WXMemberInfo.class);

    @Test
    public void serialize(){
        log.debug("------------------------------------------------------------------------------------");
        byte[] bytes1 = ProtostuffIOUtil.toByteArray(new WXMemberInfo(), protostuffSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        byte[] bytes2 = new SerializingConverter().convert(new WXMemberInfo());//大小只有bytes1的1/5
        log.debug("bytes1.length={}", bytes1.length);
        log.debug("bytes2.length={}", bytes2.length);
        WXMemberInfo wxMemberInfo1 = protostuffSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes1, wxMemberInfo1, protostuffSchema);

        WXMemberInfo wxMemberInfo2 = (WXMemberInfo)new DeserializingConverter().convert(bytes2);

        log.debug("wxMemberInfo1={}", wxMemberInfo1);
        log.debug("wxMemberInfo2={}", wxMemberInfo2);
    }

}
