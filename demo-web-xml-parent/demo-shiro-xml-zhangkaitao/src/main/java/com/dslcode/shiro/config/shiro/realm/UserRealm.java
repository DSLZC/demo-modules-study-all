package com.dslcode.shiro.config.shiro.realm;

import com.dslcode.shiro.entity.User;
import com.dslcode.shiro.service.UserService;
import lombok.Data;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Data
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    private Cache<String, User> userCache;

    @Autowired
    public void initUserCache(CacheManager cacheManager){
        this.userCache = cacheManager.getCache("currentUserCache");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        User user = this.userCache.get(username);
        if(null == user) {
            user = userService.findByUsername(username);
            this.userCache.put(username, user);
        }

        if(user == null) throw new UnknownAccountException();//没找到帐号

        if(Boolean.TRUE.equals(user.getLocked())) throw new LockedAccountException(); //帐号锁定

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getUsername()),//salt = username
                super.getName()  //realm name
        );
        return authenticationInfo;
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
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
