package com.dslcode.demo.order.redis_queue.service;

import com.dslcode.core.exception.ExceptionHandler;
import com.dslcode.demo.order.domain.OrderItem;

/**
 * Created by dongsilin on 2017/4/7.
 */
public interface RedisQueueOrderService {
    String add(Long goodsId, Long userId, Integer num) throws Exception;

    /**
     * 延迟创建订单
     * @param code
     */
    OrderItem delayCreate(String code) throws ExceptionHandler;
}
