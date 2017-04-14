package com.dslcode.demo.order.redis_queue.service.impl;

import com.dslcode.demo.order.Constants;
import com.dslcode.demo.order.domain.Goods;
import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.redis_queue.pojo.RedisOrderQueue;
import com.dslcode.demo.order.redis_queue.service.HandlerOrderService;
import com.dslcode.demo.order.repository.GoodsRepository;
import com.dslcode.demo.order.repository.SubmitOrderItemRepository;
import com.dslcode.demo.order.traditional.service.RedisDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/4/10.
 */
@Slf4j
@Service
public class HandlerOrderServiceImpl implements HandlerOrderService {

    @Autowired
    private RedisDataService redisDataService;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SubmitOrderItemRepository submitOrderItemRepository;

    @Override
    @Transactional
    public SubmitOrderItem saveOrder(RedisOrderQueue orderQueue) {
        int stock = Integer.parseInt(redisDataService.stringGet(Constants.REDIS_GOODS_STOCK_NAME + orderQueue.getGoodsId()));

        SubmitOrderItem submitOrderItem = new SubmitOrderItem(orderQueue.getNum(), orderQueue.getGoodsId(), orderQueue.getUserId(), orderQueue.getCode());
        submitOrderItem.setSuccess(stock >= orderQueue.getNum());
        this.submitOrderItemRepository.save(submitOrderItem);

        if(stock >= orderQueue.getNum()) {
            Goods goods = goodsRepository.findOne(orderQueue.getGoodsId());
            goods.setStock(goods.getStock() - orderQueue.getNum());
            this.goodsRepository.save(goods);
            redisDataService.stringPut(Constants.REDIS_GOODS_STOCK_NAME + orderQueue.getGoodsId(), String.valueOf(goods.getStock()));
        }

        return submitOrderItem;
    }


}
