package com.demo.service;


import com.demo.entity.t1.SystemUser;
import com.demo.entity.t2.SystemDept;

import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */
public interface SystemDeptService {
    List<SystemDept> findAll();

    SystemUser getCreateUser(Long userId);

    List<SystemUser> getUsers(Integer deptId);
}
