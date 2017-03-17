package com.dslcode.sessoin.redis.controller;

import com.dslcode.sessoin.redis.config.JsonRedisTemplate;
import com.dslcode.sessoin.redis.config.SerializerRedisTemplate;
import com.dslcode.sessoin.redis.pojo.WXMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by dongsilin on 2017/3/8.
 */
@RestController
@RequestMapping("redis")
public class TestController {

    @Autowired
    private JsonRedisTemplate<WXMemberInfo> jsonRedisTemplate;
    @Autowired
    private SerializerRedisTemplate<WXMemberInfo> serializerRedisTemplate;

    @PostMapping("session/put")
    public void sessionPut(HttpSession session){
        session.setAttribute("session2", new WXMemberInfo());
    }

    @GetMapping("session/get")
    public Object sessionGet(String name, HttpSession session){
        return session.getAttribute("session2");
    }

    @PostMapping("json/put")
    public void jsonPut(){
        try {
            jsonRedisTemplate.set("json2", new WXMemberInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("json/get")
    public Object jsonGet(){
        try {
            WXMemberInfo wxMemberInfo = jsonRedisTemplate.get("json2", WXMemberInfo.class);
            return wxMemberInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("serializer/put")
    public void serializerPut(){
        try {
            serializerRedisTemplate.set("serializer2", new WXMemberInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("serializer/get")
    public Object serializerGet(){
        try {
            return serializerRedisTemplate.get("serializer2");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
