package com.demo.service.impl;

import com.demo.entity.t1.SystemUser;
import com.demo.entity.t2.SystemDept;
import com.demo.repository.t1.SystemUserRepository;
import com.demo.repository.t2.SystemDeptRepository;
import com.demo.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */

@Service
@Transactional
@Slf4j
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SystemDeptRepository systemDeptRepository;

    @Override
    public List<SystemUser> findAll() {
        return this.systemUserRepository.findAll();
    }

    @Override
    public SystemDept getDept(Integer deptId) {
        return this.systemDeptRepository.findOne(deptId);
    }


}
