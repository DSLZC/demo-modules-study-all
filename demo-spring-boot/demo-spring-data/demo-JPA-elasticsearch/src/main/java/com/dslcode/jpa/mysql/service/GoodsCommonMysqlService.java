package com.dslcode.jpa.mysql.service;

import com.dslcode.jpa.mysql.entity.GoodsCommonMysql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created by dongsilin on 2017/7/31.
 */
public interface GoodsCommonMysqlService {

    Page<GoodsCommonMysql> findByPage(Pageable page);

    Page<GoodsCommonMysql> findByName(String name, Pageable page);

    long count();
}
