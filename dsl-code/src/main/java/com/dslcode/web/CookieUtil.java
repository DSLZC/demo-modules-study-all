package com.dslcode.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dongsilin on 2017/1/4.
 * Http Cookie 工具类
 */
public class CookieUtil {

    /**
     * 根据cookie名称获取cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie get(HttpServletRequest request, String cookieName){
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals(cookieName)) return cookie;
        }
        return null;
    }

    /**
     * 根据cookie名称移除cookie
     * @param cookieName
     * @param response
     */
    public static void remove(String cookieName, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);// 立即销毁cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     * @param cookieName cookie名称
     * @param cookieValue
     * @param maxAge 生存时间，秒
     * @param response
     */
    public static Cookie add(String cookieName,String cookieValue, int maxAge, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * 更新cookie值
     * @param request
     * @param response
     * @param cookieName
     * @param newValue
     * @return
     */
    public static Cookie updateValue(HttpServletRequest request, HttpServletResponse response, String cookieName, String newValue){
        Cookie cookie = get(request, cookieName);
        cookie.setValue(newValue);
        response.addCookie(cookie);
        return cookie;
    }

}
