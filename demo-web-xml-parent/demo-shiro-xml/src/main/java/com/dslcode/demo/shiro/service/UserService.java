package com.dslcode.demo.shiro.service;

import com.dslcode.demo.shiro.domain.SystemUser;

import java.util.List;

/**
 * Created by dongsilin on 2017/4/19.
 */
public interface UserService {
    SystemUser getAdmin();

    List<SystemUser> allUsers();
}
