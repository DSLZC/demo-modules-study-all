package com.dslcode.demo.order.redis_queue.service;

import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.redis_queue.pojo.RedisOrderQueue;

/**
 * Created by dongsilin on 2017/4/10.
 */
public interface HandlerOrderService {
    SubmitOrderItem saveOrder(RedisOrderQueue orderQueue);

}
