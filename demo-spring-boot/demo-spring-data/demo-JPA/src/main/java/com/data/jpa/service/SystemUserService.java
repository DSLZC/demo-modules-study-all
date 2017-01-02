package com.data.jpa.service;

import com.data.jpa.entity.SystemUser;

import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */
public interface SystemUserService{
    List<SystemUser> findAll();

}
