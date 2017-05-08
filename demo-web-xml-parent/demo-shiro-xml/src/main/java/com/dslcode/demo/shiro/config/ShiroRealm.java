package com.dslcode.demo.shiro.config;

import com.dslcode.demo.shiro.domain.SystemRole;
import com.dslcode.demo.shiro.domain.SystemUser;
import com.dslcode.demo.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/4/18.
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 认证（登录）会被Shiro调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        // 模拟数据库查找
        log.debug("----------模拟数据库用户查找------------");
        SystemUser user = userService.allUsers().stream().filter(systemUser -> systemUser.getUsername().equals(username)).findFirst().orElseGet(() -> null);

        Session session = SecurityUtils.getSubject().getSession();
        Object pwdErrorNum = session.getAttribute("pwdErrorNum");
        if(null != pwdErrorNum && (Integer) pwdErrorNum > 2) throw new ExcessiveAttemptsException();
        if (null == user) throw new UnknownAccountException();

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user, // 当前用户
                user.getPassword(),// 数据库密码
                ByteSource.Util.bytes(String.valueOf(user.getId())), //加盐
                super.getName()
        );

        return info;
    }

    /**
     * 授权会被Shiro调用
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SystemUser user = (SystemUser)principals.getPrimaryPrincipal();
        Set<String> roleNames = user.getRoles().stream().map(SystemRole::getRoleName).collect(Collectors.toCollection(HashSet::new));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        return info;
    }
}

//public class ShiroRealm implements Realm {
//    public String getName() {
//        return null;
//    }
//
//    public boolean supports(AuthenticationToken authenticationToken) {
//        return false;
//    }
//
//    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        return null;
//    }
//}
