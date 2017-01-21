package com.jpa.datatables.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dongsilin on 2017/1/4.
 */
@Slf4j
@Controller
public class WebAppController {
    @Value("${app.templates.root.path}")
    private String rootPath;

    @GetMapping("/home")
    public String home(){
        log.debug("------------------ home page ---------------------");
        return rootPath + "home";
    }

    @GetMapping("/index")
    public String index(){
        log.debug("------------------ index page ---------------------");
        return rootPath + "index";
    }

    @GetMapping("/page/**")
    public String toPage(HttpServletRequest request){
        String uri = request.getRequestURI();
        log.debug("page.request.path={}", uri);
        return rootPath + uri.substring(6);
    }


}
