package com.dslcode.shiro.config.shiro.realm;

import com.dslcode.shiro.config.Constants;
import com.dslcode.shiro.entity.User;
import com.dslcode.shiro.service.UserService;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 单点登录realm
 */
@Data
public class UserRealm extends CasRealm {

    @Autowired
    private UserService userService;

    private Cache<String, User> userCache;

    @Autowired
    public void initUserCache(CacheManager cacheManager){
        this.userCache = cacheManager.getCache("currentUserCache");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.print("+++++++++++++++++++++++++++ my cas client.node1.doGetAuthorizationInfo++++++++++++++++++++++++++++++++++++++++++");
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.print("+++++++++++++++++++++++++++ my cas client.node1.doGetAuthenticationInfo++++++++++++++++++++++++++++++++++++++++++");
        AuthenticationInfo authenticationInfo = super.doGetAuthenticationInfo(token);

        String username = (String) authenticationInfo.getPrincipals().getPrimaryPrincipal();

        User user = this.userCache.get(username);
        if(null == user) {
            user = userService.findByUsername(username);
            if(null == user) throw new UnknownAccountException();//用户不存在
            this.userCache.put(username, user);
        }

        SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER, user);

        if(Boolean.TRUE.equals(user.getLocked())) throw new LockedAccountException(); //帐号锁定

        return authenticationInfo;
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 清空用户关联权限
     * @param username
     */
    public void clearCachedAuthorizationInfo(String username){
        clearCachedAuthorizationInfo(new SimplePrincipalCollection(username, getName()));
    }

    /**
     * 清空用户认证信息
     * @param username
     */
    public void clearCachedAuthenticationInfo(String username){
        clearCachedAuthenticationInfo(new SimplePrincipalCollection(username, getName()));
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    public void clearUserCache(String username) {
        this.userCache.remove(username);
    }

    public void clearAllUserCache() {
        this.userCache.clear();
    }

}
