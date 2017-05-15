package com.dslcode.shiro.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 * 同一账户同时在线人数控制Filter
 */
public class KickoutSessionHandlerFilter extends AccessControlFilter {

    /** 踢出后转到的地址 */
    private String kickoutToUrl;
    /** 踢出之前/之后登录的用户， 默认踢出之前登录的用户 */
    private boolean kickoutTheFormer = true;
    /** 同一个帐号最大登录用户数 默认1 */
     private int maxLoginNum = 1;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();

        //如果没有登录，直接进行之后的流程
        if(!(subject.isAuthenticated() || subject.isRemembered())) return true;

        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        //TODO 同步控制
        Deque<Serializable> deque = cache.get(username);
        if(null == deque) {
            deque = new LinkedList<>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxLoginNum) {
            Serializable kickoutSessionId = null;

            if(kickoutTheFormer) kickoutSessionId = deque.removeLast(); // 踢出前者
            else kickoutSessionId = deque.removeFirst(); // 踢出后者

            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                //设置会话的kickout属性表示踢出了
                if(kickoutSession != null) kickoutSession.setAttribute("kickout", true);
            } catch (Exception e) {}
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (null != session.getAttribute("kickout")) {
            subject.logout();//会话被踢出了
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutToUrl);// 重定向踢出地址
            return false;
        }

        return true;
    }

    public String getKickoutToUrl() {
        return kickoutToUrl;
    }

    public void setKickoutToUrl(String kickoutToUrl) {
        this.kickoutToUrl = kickoutToUrl;
    }

    public boolean isKickoutTheFormer() {
        return kickoutTheFormer;
    }

    public void setKickoutTheFormer(boolean kickoutTheFormer) {
        this.kickoutTheFormer = kickoutTheFormer;
    }

    public int getMaxLoginNum() {
        return maxLoginNum;
    }

    public void setMaxLoginNum(int maxLoginNum) {
        this.maxLoginNum = maxLoginNum;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

}
