package com.demo.controller;

import com.demo.entity.t1.SystemUser;
import com.demo.service.SystemUserService;
import com.dslcode.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dongsilin on 2016/12/31.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("/all")
    public WebResponse allUser(){
        List<SystemUser> users = systemUserService.findAll();
        users.forEach(user -> user.setDept(systemUserService.getDept(user.getDeptId())));
        log.debug("users={}", users);
        return WebResponse.buildSuccessData(users);
    }

}
