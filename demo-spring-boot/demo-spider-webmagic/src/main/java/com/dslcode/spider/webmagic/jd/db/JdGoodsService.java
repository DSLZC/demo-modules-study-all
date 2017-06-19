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
public class JdGoodsService {

    @Autowired
    private JdGoodsRepository jdGoodsRepository;
    @Autowired
    private JdGoodsCommonService jdGoodsCommonService;

    @Transactional
    public void save(JdGoods jdGoods) {

        String threadName = Thread.currentThread().getName();
        log.debug("---------------JdGoodsCommon query begin-------------------" + threadName);
        JdGoodsCommon goodsCommon = this.jdGoodsCommonService.findByMainSkuId(jdGoods.getMainSkuId());
        if (null == goodsCommon){
            log.debug("---------------JdGoodsCommon save begin-------------------" + threadName);
            goodsCommon = new JdGoodsCommon(jdGoods);
            goodsCommon = this.jdGoodsCommonService.save(goodsCommon);
            log.debug("---------------JdGoodsCommon save over-------------------" + threadName);
        }

        jdGoods.setGoodsCommonId(goodsCommon.getId());
        this.jdGoodsRepository.save(jdGoods);
    }

}
