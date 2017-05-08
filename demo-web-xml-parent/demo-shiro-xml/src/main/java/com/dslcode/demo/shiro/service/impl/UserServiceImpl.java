package com.dslcode.demo.shiro.service.impl;

import com.dslcode.demo.shiro.domain.RoleEnum;
import com.dslcode.demo.shiro.domain.SystemRole;
import com.dslcode.demo.shiro.domain.SystemUser;
import com.dslcode.demo.shiro.service.PasswordHelper;
import com.dslcode.demo.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dongsilin on 2017/4/19.
 */
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_CREDENTTIAL = "123456";
    private List<SystemUser> users = Arrays.asList(
            new SystemUser(1L, "admin", PasswordHelper.encryptPassword(DEFAULT_CREDENTTIAL, "1"), "超级管理员", SystemRole.generateRoles(RoleEnum.values())),
            new SystemUser(2L, "manager", PasswordHelper.encryptPassword(DEFAULT_CREDENTTIAL, "2"), "管理员", SystemRole.generateRoles(RoleEnum.manager, RoleEnum.user, RoleEnum.visitor)),
            new SystemUser(3L, "user", PasswordHelper.encryptPassword(DEFAULT_CREDENTTIAL, "3"), "普通用户", SystemRole.generateRoles(RoleEnum.user, RoleEnum.visitor)),
            new SystemUser(4L, "visitor", PasswordHelper.encryptPassword(DEFAULT_CREDENTTIAL, "4"), "访客", SystemRole.generateRoles(RoleEnum.visitor))
            );

    @Override
    @RequiresRoles("admin")
    public SystemUser getAdmin() {
        return this.users.stream().filter(user -> user.getUsername().equals("admin")).findFirst().orElseGet(() -> null);
    }

    @Override
    public List<SystemUser> allUsers() {
        return this.users;
    }


}
