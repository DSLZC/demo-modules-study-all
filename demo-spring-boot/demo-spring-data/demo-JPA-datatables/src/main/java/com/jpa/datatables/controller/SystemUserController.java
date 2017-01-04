package com.jpa.datatables.controller;

import com.dslcode.web.response.WebResponse;
import com.jpa.datatables.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongsilin on 2017/1/4.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("/all")
    public WebResponse allUser(){
        return WebResponse.buildSuccessData(this.systemUserService.findAll());
    }
}
