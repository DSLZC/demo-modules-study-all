package com.demo.service.impl;

import com.demo.entity.t1.SystemUser;
import com.demo.entity.t2.SystemDept;
import com.demo.repository.t1.SystemUserRepository;
import com.demo.repository.t2.SystemDeptRepository;
import com.demo.service.SystemDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */

@Service
@Transactional
@Slf4j
public class SystemDeptServiceImpl implements SystemDeptService {

    @Autowired
    private SystemDeptRepository systemDeptRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;


    @Override
    public List<SystemDept> findAll() {
        return this.systemDeptRepository.findAll();
    }

    @Override
    public SystemUser getCreateUser(Long userId) {
        return this.systemUserRepository.findOne(userId);
    }

    @Override
    public List<SystemUser> getUsers(Integer deptId) {
        return this.systemUserRepository.findAll((Root<SystemUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) -> cb.equal(root.get("deptId"), deptId));
    }

}
