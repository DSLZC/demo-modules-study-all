package com.dslcode.demo.shiro.controller;

import com.dslcode.demo.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dongsilin on 2017/4/19.
 */
@Slf4j
@Controller
@RequestMapping("page")
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    @RequiresRoles(value = "visitor", logical = Logical.AND)
    public String list(){
        log.info("====================> list page");

        return "list";
    }

    @GetMapping("admin")
    @RequiresRoles("admin")
    public String admin(){
        log.info("====================> admin page");
        log.info("admin={}", userService.getAdmin());
        return "admin";
    }

    @GetMapping("user")
    @RequiresRoles(value = {"user"}, logical = Logical.AND)
    public String user(){
        log.info("====================> user page");
        return "user";
    }

    @GetMapping("visitor")
    @RequiresRoles(value = {"visitor"}, logical = Logical.AND)
    public String visitor(){
        log.info("====================> visitor page");
        return "visitor";
    }
}
