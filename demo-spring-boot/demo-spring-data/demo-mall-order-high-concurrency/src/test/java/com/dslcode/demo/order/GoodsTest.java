package com.dslcode.demo.order;

import com.dslcode.demo.order.traditional.service.TraditionalGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
public class GoodsTest extends DemoMallOrderHighConcurrencyApplicationTests {

    @Autowired
    private TraditionalGoodsService goodsService;

    @Test
    public void initData(){
        goodsService.initData();
    }

    @Test
    public void add(){
        goodsService.initData();
    }
}
