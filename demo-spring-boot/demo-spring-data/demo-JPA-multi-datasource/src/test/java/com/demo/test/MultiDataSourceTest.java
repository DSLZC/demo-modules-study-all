package com.demo.test;


import com.demo.DemoJpaMultiDatasourceApplicationTests;
import com.demo.service.SystemDeptService;
import com.demo.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2016/12/30.
 */
@Slf4j
public class MultiDataSourceTest extends DemoJpaMultiDatasourceApplicationTests {

    @Autowired
    private SystemDeptService systemDeptService;
    @Autowired
    private SystemUserService systemUserService;

    @Test
    @Transactional(readOnly = true)
    public void allUser(){
        systemUserService.findAll().forEach(user ->{
            user.setDept(systemUserService.getDept(user.getDeptId()));
            log.debug("user={}", user);
        });
        systemDeptService.findAll().forEach(dept ->{
            dept.setCreateUser(systemDeptService.getCreateUser(dept.getCreateUserId()));
            dept.setUsers(systemDeptService.getUsers(dept.getId()));
            log.debug("dept={}", dept);
        });
    }
}
