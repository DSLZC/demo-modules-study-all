package com.dslcode.jpa.elasticsearch.repository;

import com.dslcode.jpa.elasticsearch.type.GoodsCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Created by dongsilin on 2017/7/31.
 */
public interface GoodsCommonRepository extends ElasticsearchRepository<GoodsCommon, Long> {
    Page<GoodsCommon> findByStoreId(Long storeId, Pageable page);

    Page<GoodsCommon> findByName(String name, Pageable page);


}
