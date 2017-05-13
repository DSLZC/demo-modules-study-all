package com.dslcode.shiro.controller;

import com.dslcode.shiro.config.jcaptcha.ManageJCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dongsilin on 2017/5/12.
 */
@Slf4j
@Controller
@RequestMapping("captcha")
public class JCaptchaResource {

    @GetMapping("generate")
    public void generateImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(
                    ManageJCaptchaService.generateImage(request),
                    "jpg",
                    out
            );
            out.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            out.close();
        }

    }

}
