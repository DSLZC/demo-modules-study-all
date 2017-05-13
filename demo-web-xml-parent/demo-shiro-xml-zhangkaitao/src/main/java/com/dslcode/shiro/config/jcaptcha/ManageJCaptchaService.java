/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.dslcode.shiro.config.jcaptcha;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-22 下午3:38
 * <p>Version: 1.0
 */
public class ManageJCaptchaService extends DefaultManageableImageCaptchaService {

    private static final boolean jcaptchaEnabled = true; //是否开启验证码支持

    private static final String jcaptchaParamName = "captchaCode"; //前台提交的验证码参数名

    public static final DefaultManageableImageCaptchaService captchaService =
            new DefaultManageableImageCaptchaService(
                    new FastHashMapCaptchaStore(),
                    new SampleListImageCaptchaEngine(),
                    180, 100000, 75000
            );

    public ManageJCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCapcha(String id, String userCaptchaResponse) {
        return store.getCaptcha(id).validateResponse(userCaptchaResponse);
    }

    /**
     * 生成验证码
     * @param request
     * @return
     */
    public static BufferedImage generateImage(HttpServletRequest request) {
        return captchaService.getImageChallengeForID(request.getRequestedSessionId());
    }

    /**
     * 校验验证码
     * @param request
     * @throws Exception
     */
    public static void validate(HttpServletRequest request) throws Exception {
        if (!jcaptchaEnabled) return;
        Boolean success = captchaService.validateResponseForID(request.getRequestedSessionId(), request.getParameter(jcaptchaParamName));
        if (null == success || Boolean.FALSE.equals(success)) throw new Exception();
    }

}
