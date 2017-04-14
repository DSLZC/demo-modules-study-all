package com.dslcode.demo.order.redis_queue.controller;

import com.dslcode.demo.order.redis_queue.service.RedisQueueOrderService;
import com.dslcode.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
@RestController
@RequestMapping("redis_queue/order")
public class RedisQueueOrderController {

    @Autowired
    private RedisQueueOrderService orderService;

    @GetMapping("hi")
    public WebResponse hi(){
        return WebResponse.buildSuccessMsg("hello world");
    }

    @PostMapping("add")
    public WebResponse addOrder(Long goodsId, Long userId, Integer num){
        try {
            String code = this.orderService.add(goodsId, userId, num);
            log.debug("-------------------------->{}, code={}", Thread.currentThread().getName(), code);
            Thread.sleep(1000L);
            this.orderService.delayCreate(code);
            return WebResponse.buildSuccessData(code);
        } catch (Exception e) {
            log.error("", e);
            return WebResponse.buildErrorMsg(e.getMessage());
        }
    }

    @PostMapping("adds")
    public void concurrentAdd(){
        new Thread(() -> addOrder(1l, 1l, 1), "one").start();
        new Thread(() -> addOrder(1l, 2l, 2), "two").start();
        new Thread(() -> addOrder(1l, 3l, 3), "three").start();
        new Thread(() -> addOrder(1l, 4l, 4), "four").start();
        new Thread(() -> addOrder(1l, 5l, 5), "five").start();
        new Thread(() -> addOrder(1l, 6l, 6), "six").start();
        new Thread(() -> addOrder(1l, 7l, 7), "seven").start();

        new Thread(() -> addOrder(2l, 1l, 1), "one").start();
        new Thread(() -> addOrder(2l, 2l, 2), "two").start();
        new Thread(() -> addOrder(2l, 3l, 3), "three").start();
        new Thread(() -> addOrder(2l, 4l, 4), "four").start();
        new Thread(() -> addOrder(2l, 5l, 5), "five").start();
        new Thread(() -> addOrder(2l, 6l, 6), "six").start();
        new Thread(() -> addOrder(2l, 7l, 7), "seven").start();

        new Thread(() -> addOrder(3l, 1l, 1), "one").start();
        new Thread(() -> addOrder(3l, 2l, 2), "two").start();
        new Thread(() -> addOrder(3l, 3l, 3), "three").start();
        new Thread(() -> addOrder(3l, 4l, 4), "four").start();
        new Thread(() -> addOrder(3l, 5l, 5), "five").start();
        new Thread(() -> addOrder(3l, 6l, 6), "six").start();
        new Thread(() -> addOrder(3l, 7l, 7), "seven").start();

        new Thread(() -> addOrder(4l, 1l, 1), "one").start();
        new Thread(() -> addOrder(4l, 2l, 2), "two").start();
        new Thread(() -> addOrder(4l, 3l, 3), "three").start();
        new Thread(() -> addOrder(4l, 4l, 4), "four").start();
        new Thread(() -> addOrder(4l, 5l, 5), "five").start();
        new Thread(() -> addOrder(4l, 6l, 6), "six").start();
        new Thread(() -> addOrder(4l, 7l, 7), "seven").start();

        new Thread(() -> addOrder(5l, 1l, 1), "one").start();
        new Thread(() -> addOrder(5l, 2l, 2), "two").start();
        new Thread(() -> addOrder(5l, 3l, 3), "three").start();
        new Thread(() -> addOrder(5l, 4l, 4), "four").start();
        new Thread(() -> addOrder(5l, 5l, 5), "five").start();
        new Thread(() -> addOrder(5l, 6l, 6), "six").start();
        new Thread(() -> addOrder(5l, 7l, 7), "seven").start();
    }

}
