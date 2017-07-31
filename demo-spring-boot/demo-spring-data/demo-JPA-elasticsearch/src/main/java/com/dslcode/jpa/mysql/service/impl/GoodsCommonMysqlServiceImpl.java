package com.dslcode.jpa.mysql.service.impl;

import com.dslcode.jpa.mysql.entity.GoodsCommonMysql;
import com.dslcode.jpa.mysql.repository.GoodsCommonMysqlRepository;
import com.dslcode.jpa.mysql.service.GoodsCommonMysqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * Created by dongsilin on 2017/7/31.
 */
@Slf4j
@Service
public class GoodsCommonMysqlServiceImpl implements GoodsCommonMysqlService {

    @Autowired
    private GoodsCommonMysqlRepository goodsCommonRepository;


    @Override
    public Page<GoodsCommonMysql> findByPage(Pageable page) {
        return this.goodsCommonRepository.findAll(page);
    }

    @Override
    public Page<GoodsCommonMysql> findByName(String name, Pageable page) {
        return this.goodsCommonRepository.findAll(
                (Root<GoodsCommonMysql> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) -> cb.like(root.get("name"), "%" + name + "%"),
                page
        );
    }

    @Override
    public long count() {
        return this.goodsCommonRepository.count();
    }
}
