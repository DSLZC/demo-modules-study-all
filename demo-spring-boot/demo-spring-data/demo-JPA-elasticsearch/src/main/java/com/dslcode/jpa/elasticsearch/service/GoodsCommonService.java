package com.dslcode.jpa.elasticsearch.service;

import com.dslcode.jpa.elasticsearch.type.GoodsCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by dongsilin on 2017/7/31.
 */
public interface GoodsCommonService {
    void insert(GoodsCommon goodsCommon);

    Page<GoodsCommon> findByName(String name, Pageable page);

    Page<GoodsCommon> findByStoreId(Long storeId, Pageable page);

    void insert(List<GoodsCommon> goodsCommons);

    void delete(Long id);
    void deleteAll();

    Iterable<GoodsCommon> findAll();

    long count();
    Page<GoodsCommon> findGTPrice(BigDecimal price, Pageable page);


}
