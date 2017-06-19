package com.dslcode.spider.webmagic.jd.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/6/7.
 */
@Slf4j
@Service
public class JdGoodsCommonService {

    @Autowired
    private JdGoodsCommonRepository jdGoodsCommonRepository;

    public boolean isExist(long mainSkuId){
        long c = this.jdGoodsCommonRepository.countByMainSkuId(mainSkuId);
        return c > 0;
    }

    @Transactional
    public JdGoodsCommon save(JdGoodsCommon jdGoodsCommon){
        return this.jdGoodsCommonRepository.save(jdGoodsCommon);
    }

    public JdGoodsCommon findByMainSkuId(long mainSkuId) {
        return this.jdGoodsCommonRepository.findByMainSkuId(mainSkuId);
    }
}
