package com.dslcode.demo.order.redis_queue;

import com.dslcode.core.util.NullUtil;
import com.dslcode.demo.order.Constants;
import com.dslcode.demo.order.redis_queue.pojo.RedisOrderQueue;
import com.dslcode.demo.order.redis_queue.service.HandlerOrderService;
import com.dslcode.demo.order.traditional.service.RedisDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dongsilin on 2017/4/12.
 */
@Slf4j
@Configuration
@EnableScheduling
public class OrderScheduled {

    @Autowired
    private RedisDataService redisDataService;
    @Autowired
    private HandlerOrderService handlerOrderService;

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @Scheduled(fixedDelay=1000)
    public void start() {
        log.debug("------------------------------------------------");
        setHasNext:
        while (redisDataService.setHasNext(Constants.REDIS_ORDER_GOODS_ID_SET_NAME)){
            for (int i=0;i<5;i++){
                String idStr = redisDataService.setPop(Constants.REDIS_ORDER_GOODS_ID_SET_NAME);
                if(NullUtil.isNull(idStr)) break setHasNext;
                threadPool.submit(() ->{
                    String redisQueueStr = redisDataService.listGet(Constants.REDIS_ORDER_GOODS_QUENE_NAME + idStr);
                    while (NullUtil.isNotNull(redisQueueStr)){
                        log.debug("======================>{}, redisQueueStr={}", Thread.currentThread().getName(), redisQueueStr);
                        RedisOrderQueue orderQueue = RedisOrderQueue.parseStr(redisQueueStr);
                        log.debug("orderQueue={}", orderQueue);
                        handlerOrderService.saveOrder(orderQueue);

                        redisQueueStr = redisDataService.listGet(Constants.REDIS_ORDER_GOODS_QUENE_NAME + idStr);
                    }
                });
            }
        }

    }

}

