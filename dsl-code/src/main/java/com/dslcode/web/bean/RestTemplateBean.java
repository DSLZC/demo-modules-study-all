package com.dslcode.web.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Created by dongsilin on 2017/3/7.
 * RestTemplate bean，生命周期为session
 */
@Configuration
public class RestTemplateBean {

    private static final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    private static final HttpMessageConverter httpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    static {
        requestFactory.setReadTimeout(1000 * 30);   //30s
        requestFactory.setConnectTimeout(1000 * 30);    //30s
    }

    @Bean @Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RestTemplate initRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.getMessageConverters().set(1, httpMessageConverter);
        return restTemplate;
    }

}
