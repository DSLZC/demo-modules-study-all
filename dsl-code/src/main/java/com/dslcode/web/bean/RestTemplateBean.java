package com.dslcode.web.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by dongsilin on 2017/3/7.
 * RestTemplate bean，生命周期为session
 */
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class RestTemplateBean extends RestTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    private static final HttpMessageConverter httpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    static {
        requestFactory.setReadTimeout(1000 * 30);   //30s
        requestFactory.setConnectTimeout(1000 * 30);    //30s
    }

    @PostConstruct
    public void init(){
        this.setRequestFactory(requestFactory);
        this.getMessageConverters().set(1, httpMessageConverter);
    }

    @PreDestroy
    public void preDestroy(){
        log.debug("**********************************RestTemplateBean PreDestroy******************************");
    }

}
