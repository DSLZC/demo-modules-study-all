package com.dslcode.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
@Slf4j
public class LoginController {

    @RequestMapping("login/error")
    public String error(Model model){
        //是否被踢出登录

        model.addAttribute("error", "登录失败");

        return "error";
    }

}
