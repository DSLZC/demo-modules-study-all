package com.jpa.datatables.service.impl;

import com.jpa.datatables.entity.SystemUser;
import com.jpa.datatables.general.datatables.DataTablesInputExtend;
import com.jpa.datatables.repository.SystemUserRepository;
import com.jpa.datatables.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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

    @Override
    public DataTablesOutput datatableList(DataTablesInputExtend inputExtend) {
        return this.systemUserRepository.findAll(inputExtend);
    }


}
