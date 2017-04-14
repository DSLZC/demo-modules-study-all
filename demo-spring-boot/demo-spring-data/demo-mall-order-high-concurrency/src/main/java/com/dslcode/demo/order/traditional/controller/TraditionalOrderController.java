package com.dslcode.demo.order.traditional.controller;

import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.traditional.service.TraditionalOrderService;
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
@RequestMapping("traditional/order")
public class TraditionalOrderController {

    @Autowired
    private TraditionalOrderService orderService;

    @GetMapping("hi")
    public WebResponse hi(){
        return WebResponse.buildSuccessMsg("hello world");
    }

    @PostMapping("add")
    public WebResponse addOrder(Long goodsId, Long userId, Integer num){
        try {
            SubmitOrderItem orderItem = this.orderService.add(goodsId, userId, num);
            log.debug("-------------------------->{}, submitOrderItem={}", Thread.currentThread().getName(), orderItem);
            return WebResponse.buildSuccessData(orderItem.getCode());
        } catch (Exception e) {
            log.error("", e);
            return WebResponse.buildErrorMsg(e.getMessage());
        }
    }

    @PostMapping("adds")
    public void concurrentAdd(){
        new Thread(() -> addOrder(3l, 1l, 1), "one").start();
        new Thread(() -> addOrder(3l, 2l, 2), "two").start();
        new Thread(() -> addOrder(3l, 3l, 3), "three").start();
        new Thread(() -> addOrder(3l, 4l, 4), "four").start();
        new Thread(() -> addOrder(3l, 5l, 5), "five").start();
        new Thread(() -> addOrder(3l, 6l, 6), "six").start();
        new Thread(() -> addOrder(3l, 7l, 7), "seven").start();
    }

}
