package com.dslcode.spider.webmagic.jd.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dongsilin on 2017/6/7.
 */
public interface JdGoodsCommonRepository extends JpaRepository<JdGoodsCommon, Long>{
    long countByMainSkuId(long mainSkuId);

    JdGoodsCommon findByMainSkuId(long mainSkuId);
}
