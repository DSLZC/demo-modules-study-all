package com.data.jpa.service.impl;

import com.data.jpa.entity.SystemUser;
import com.data.jpa.repository.SystemUserRepository;
import com.data.jpa.service.SystemUserService;
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
    @Override
    public List<SystemUser> findAll() {
        return this.systemUserRepository.findAll();
    }


}
