package com.jpa.datatables.repository;

import com.jpa.datatables.entity.SystemUser;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dongsilin on 2016/12/30.
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, Long>, DataTablesRepository<SystemUser, Long> {

}
