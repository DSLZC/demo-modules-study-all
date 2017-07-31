package com.dslcode.jpa.mysql.repository;

import com.dslcode.jpa.mysql.entity.GoodsCommonMysql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongsilin on 2017/7/31.
 */
public interface GoodsCommonMysqlRepository extends JpaRepository<GoodsCommonMysql, Long>, JpaSpecificationExecutor<GoodsCommonMysql> {
}
