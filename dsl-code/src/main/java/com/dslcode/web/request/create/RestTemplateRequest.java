package com.dslcode.web.request.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by 董思林 on 2016/11/17.
 * 封装RestTemplate相关请求工具类
 */
public class RestTemplateRequest<T> {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateRequest.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(1000 * 60 * 5);// 5 minutes
        requestFactory.setConnectTimeout(1000 * 60 * 5);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    /**
     * POST请求
     * @param url
     * @param request
     * @param responseClass
     * @param params
     * @param <T>
     * @return
     */
    public static<T> T postForObject(String url, Object request, Class<T> responseClass, Map<String, ?> params){
        return null == params? restTemplate.postForObject(url, request, responseClass) : restTemplate.postForObject(url, request, responseClass, params);
    }

    /**
     * POST请求
     * @param url
     * @param request
     * @param responseClass
     * @param params
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseClass, Map<String, ?> params) {
        return null == params? restTemplate.postForEntity(url, request, responseClass) : restTemplate.postForEntity(url, request, responseClass, params);
    }

    /**
     * GET请求
     * @param url
     * @param responseClass
     * @param params
     * @param <T>
     * @return
     */
    public static<T> T getForObject(String url, Class<T> responseClass, Map<String, ?> params){
        return null == params? restTemplate.getForObject(url, responseClass) : restTemplate.getForObject(url, responseClass, params);
    }

    /**
     * GET请求
     * @param url
     * @param responseClass
     * @param params
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> getForEntity(String url, Class<T> responseClass, Map<String, ?> params) {
        return null == params? restTemplate.getForEntity(url, responseClass) : restTemplate.getForEntity(url, responseClass, params);
    }

    /**
     * exchange
     * @param url
     * @param type 请求类型 0-GET 1-POST
     * @param requestEntity
     * @param responseClass
     * @param params
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> exchange(String url, int type, HttpEntity<?> requestEntity, Class<T> responseClass, Map<String, ?> params){
        return null == params? restTemplate.exchange(url, type == 0? HttpMethod.GET : HttpMethod.POST, requestEntity, responseClass) : restTemplate.exchange(url, type == 0? HttpMethod.GET : HttpMethod.POST, requestEntity, responseClass, params);
    }

    /**
     * exchange
     * @param url
     * @param type 请求类型 0-GET 1-POST
     * @param requestEntity
     * @param responseType
     * @param params
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> exchange(String url, int type, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> params){
        return null == params? restTemplate.exchange(url, type == 0? HttpMethod.GET : HttpMethod.POST, requestEntity, responseType) : restTemplate.exchange(url, type == 0? HttpMethod.GET : HttpMethod.POST, requestEntity, responseType, params);
    }
}
