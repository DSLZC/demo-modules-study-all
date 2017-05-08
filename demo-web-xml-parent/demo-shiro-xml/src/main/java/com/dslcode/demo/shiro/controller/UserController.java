package com.dslcode.demo.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dongsilin on 2017/4/16.
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @PostMapping("login")
    public String login(
            Model model,
            String username,
            String password,
            @RequestParam(name = "rememberMe", defaultValue = "0") int rememberMe
    ){
        log.info("---------->{}, {}, {}", username, password, rememberMe);

        // 当前用户
        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        // 判断是否认证(登录)
        if(!currentUser.isAuthenticated()){
            // 封装UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(
                    username, //用户名
                    password, // 密码
                    rememberMe==1 //是否记住我
            );

            try {
                // 执行登录操作
                currentUser.login(token);
            }  catch (UnknownAccountException uae) {
                model.addAttribute("error", "用户名不存在");
                return "login";
            } catch (IncorrectCredentialsException ice) {
                Object pwdErrorNum = session.getAttribute("pwdErrorNum");
                if(null != pwdErrorNum) session.setAttribute("pwdErrorNum", ((Integer) pwdErrorNum)+1);
                else session.setAttribute("pwdErrorNum", 1);
                model.addAttribute("error", "密码错误");
                return "login";
            } catch (ExcessiveAttemptsException lae) {
                model.addAttribute("error", "密码错误次数过多，用户被锁定，请十分钟后重试");
                return "login";
            } catch (LockedAccountException e) {
                model.addAttribute("error", "用户被锁定");
                return "login";
            } catch (AuthenticationException e) {
                model.addAttribute("error", e.getMessage());
                return "login";
            } catch (Exception e){
                log.error("", e);
                return "error";
            }
        }

        session.setAttribute("pwdErrorNum", 0);
        return "redirect:/page/list";
    }

    @GetMapping("logout")
    public String logout(){
        // 当前用户
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "login";
    }


//    public static void main(String[] dd){
//        for (int i=1;i<5;i++){
//            log.info("{}", new SimpleHash("MD5","123456", i+""));
//        }
//    }
}
