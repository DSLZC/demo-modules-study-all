package com.dslcode.shiro.controller;

import com.dslcode.shiro.config.jcaptcha.ManageJCaptchaService;
import com.dslcode.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
@Slf4j
public class LoginController {

//    @RequestMapping(value = "/login")
//    public String showLoginForm(HttpServletRequest req, Model model) {
//        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
//        String error = null;
//        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(exceptionClassName != null) {
//            error = "其他错误：" + exceptionClassName;
//        }
//        model.addAttribute("error", error);
//        return "login";
//    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public WebResponse login(HttpServletRequest request, String username, String password, @RequestParam(name = "rememberMe", defaultValue = "0") int rememberMe) {

        //图片验证码
        try {
            ManageJCaptchaService.validate(request);
        } catch (Exception e) {
            return WebResponse.buildErrorMsg("图片验证码错误");
        }

        // 当前用户
        Subject currentUser = SecurityUtils.getSubject();

        // 判断是否认证(登录)
        if(!currentUser.isAuthenticated()){
            // 封装UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(
                    username, //用户名
                    password, // 密码
                    rememberMe == 1 //是否记住我
            );

            try {
                // 执行登录操作
                currentUser.login(token);
            }  catch (UnknownAccountException uae) {
                return WebResponse.buildErrorMsg("用户名不存在");
            } catch (IncorrectCredentialsException ice) {
                return WebResponse.buildErrorMsg("密码错误");
            } catch (ExcessiveAttemptsException lae) {
                return WebResponse.buildErrorMsg("密码错误次数过多，用户被锁定，请十分钟后重试");
            } catch (LockedAccountException e) {
                return WebResponse.buildErrorMsg("用户被锁定");
            } catch (AuthenticationException e) {
                return WebResponse.buildErrorMsg(e.getMessage());
            } catch (Exception e){
                log.error("", e);
                return WebResponse.buildErrorMsg(e.getMessage());
            }
        }
        return WebResponse.buildSuccessData("/");
    }


}
