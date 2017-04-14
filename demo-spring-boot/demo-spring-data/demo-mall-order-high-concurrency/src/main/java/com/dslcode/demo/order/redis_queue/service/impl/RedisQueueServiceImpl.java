package com.dslcode.demo.order.redis_queue.service.impl;

import com.dslcode.core.exception.ExceptionHandler;
import com.dslcode.demo.order.Constants;
import com.dslcode.demo.order.domain.OrderItem;
import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.redis_queue.pojo.RedisOrderQueue;
import com.dslcode.demo.order.redis_queue.service.RedisQueueOrderService;
import com.dslcode.demo.order.repository.GoodsRepository;
import com.dslcode.demo.order.repository.OrderItemRepository;
import com.dslcode.demo.order.repository.SubmitOrderItemRepository;
import com.dslcode.demo.order.traditional.service.RedisDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
@Service
public class RedisQueueServiceImpl implements RedisQueueOrderService {

    @Autowired
    private OrderItemRepository orderRepository;
    @Autowired
    private SubmitOrderItemRepository submitOrderItemRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private RedisDataService redisDataService;

    @Override
    public String add(Long goodsId, Long userId, Integer num) throws Exception {
        Integer stock = Integer.parseInt(redisDataService.stringGet(Constants.REDIS_GOODS_STOCK_NAME + goodsId));
        if (num > stock) throw new Exception("商品库存不足");
        RedisOrderQueue orderQueue = new RedisOrderQueue(userId, goodsId, num);
        redisDataService.listPut(Constants.REDIS_ORDER_GOODS_QUENE_NAME + goodsId, orderQueue.getRedisQueueStr());
        redisDataService.setPut(Constants.REDIS_ORDER_GOODS_ID_SET_NAME, String.valueOf(goodsId));
        return orderQueue.getCode();
    }

    @Override
    @Transactional
    public OrderItem delayCreate(String code) throws ExceptionHandler {
        SubmitOrderItem submitOrderItem = this.submitOrderItemRepository.findByCode(code);
        if(null != submitOrderItem && submitOrderItem.getSuccess()){
            // 订单入库
            OrderItem orderItem = new OrderItem(submitOrderItem.getNum(), submitOrderItem.getGoodsId(), submitOrderItem.getUserId(), 1, new Date());
            this.orderRepository.save(orderItem);
            return orderItem;
        }else throw new ExceptionHandler("订单生成失败");
    }

}
