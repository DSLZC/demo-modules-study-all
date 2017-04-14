package com.dslcode.demo.order.traditional.service.impl;

import com.dslcode.demo.order.Constants;
import com.dslcode.demo.order.domain.Goods;
import com.dslcode.demo.order.repository.GoodsRepository;
import com.dslcode.demo.order.traditional.service.RedisDataService;
import com.dslcode.demo.order.traditional.service.TraditionalGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
@Service
public class TraditionalGoodsServiceImpl implements TraditionalGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private RedisDataService redisDataService;


    @Override
    @Transactional
    public void initData(){
        List<Goods> goodses = new ArrayList<>(5);
        goodses.add(new Goods("iPhone7 plus", 10, new BigDecimal(1000)));
        goodses.add(new Goods("小米手环", 5, new BigDecimal(99)));
        goodses.add(new Goods("男士白色时髦短袖", 3, new BigDecimal(20)));
        goodses.add(new Goods("spring 框架视频300G", 20, new BigDecimal(15)));
        goodses.add(new Goods("美的电风扇 清爽一夏", 8, new BigDecimal(45)));

        this.goodsRepository.save(goodses);
    }

    @Override
    public void initRedisData() {
        this.goodsRepository.findAll().stream().forEach(goods -> {
            redisDataService.stringPut(Constants.REDIS_GOODS_STOCK_NAME + goods.getId(), String.valueOf(goods.getStock()));
        });
    }

}
