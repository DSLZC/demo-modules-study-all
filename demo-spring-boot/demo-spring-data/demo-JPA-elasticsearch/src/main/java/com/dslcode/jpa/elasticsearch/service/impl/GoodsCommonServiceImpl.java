package com.dslcode.jpa.elasticsearch.service.impl;

import com.dslcode.jpa.elasticsearch.repository.GoodsCommonRepository;
import com.dslcode.jpa.elasticsearch.service.GoodsCommonService;
import com.dslcode.jpa.elasticsearch.type.GoodsCommon;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dongsilin on 2017/7/31.
 */
@Slf4j
@Service
public class GoodsCommonServiceImpl implements GoodsCommonService {

    @Autowired
    private GoodsCommonRepository goodsCommonRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void insert(GoodsCommon goodsCommon) {
        this.goodsCommonRepository.save(goodsCommon);
    }

    @Override
    public Page<GoodsCommon> findByName(String name, Pageable page) {
        return this.goodsCommonRepository.search(new QueryStringQueryBuilder(name).field("name"), page);
//        return this.goodsCommonRepository.findByName(name, page);
    }

    @Override
    public Page<GoodsCommon> findByStoreId(Long storeId, Pageable page) {
        return this.goodsCommonRepository.findByStoreId(storeId, page);
    }

    @Override
    public Page<GoodsCommon> findGTPrice(BigDecimal price, Pageable page) {
        return this.goodsCommonRepository.search(new RangeQueryBuilder("price").gte(price), page);
    }

    @Override
    public void insert(List<GoodsCommon> goodsCommons) {
        this.goodsCommonRepository.save(goodsCommons);
    }

    @Override
    public void delete(Long id) {
        this.goodsCommonRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        this.goodsCommonRepository.deleteAll();
    }

    @Override
    public Iterable<GoodsCommon> findAll() {
        return this.goodsCommonRepository.findAll();
    }

    @Override
    public long count() {
        return this.goodsCommonRepository.count();
    }
}
