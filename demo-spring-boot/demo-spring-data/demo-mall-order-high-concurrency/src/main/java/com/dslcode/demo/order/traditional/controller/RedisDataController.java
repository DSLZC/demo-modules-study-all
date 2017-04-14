package com.dslcode.demo.order.traditional.controller;

import com.dslcode.demo.order.traditional.service.TraditionalGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongsilin on 2017/4/8.
 */
@Slf4j
@RestController
@RequestMapping("redis")
public class RedisDataController {

    @Autowired
    private TraditionalGoodsService goodsService;

    @PostMapping("init")
    public void init(){
        goodsService.initRedisData();
    }

}
