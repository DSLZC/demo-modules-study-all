package com.aop.controller;

import com.dslcode.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dongsilin on 2016/12/30.
 */
@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/hello")
    public WebResponse hello(HttpServletRequest request){
        log.debug("-------------- hello --------------------");
        return WebResponse.buildSuccessMsg("good afternoon!!!");
    }
}
