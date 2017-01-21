package com.jpa.datatables.service;


import com.jpa.datatables.entity.SystemUser;
import com.jpa.datatables.general.datatables.DataTablesInputExtend;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

/**
 * Created by dongsilin on 2016/12/30.
 */
public interface SystemUserService{
    List<SystemUser> findAll();

    DataTablesOutput datatableList(DataTablesInputExtend inputExtend);
}
