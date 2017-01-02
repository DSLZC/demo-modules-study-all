package com.dslcode.core.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dongsilin on 2016/11/16.
 * Json工具类
 */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * JSON字符串转对象
     * @param json JSON字符串
     * @param c 目标对象class
     * @return 目标对象实例
     */
    public static<T> T readJson(String json, Class<T> c){
        try {
            return mapper.readValue(json, c);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象转JSON字符串
     * @param o 源对象实例
     * @return JSON字符串
     */
    public static String toJson(Object o){
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
