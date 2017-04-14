package com.dslcode.demo.order;

import com.dslcode.demo.order.traditional.controller.TraditionalOrderController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
public class OrderTest extends DemoMallOrderHighConcurrencyApplicationTests {

    @Autowired
    private TraditionalOrderController orderController;


    @Test
    public void add(){
        orderController.addOrder(3l, 1l, 2);
        new Thread(() -> orderController.addOrder(3l, 1l, 2)).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                orderController.addOrder(3l, 5l, 1);
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                orderController.addOrder(3l, 4l, 3);
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                orderController.addOrder(3l, 3l, 1);
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                orderController.addOrder(3l, 2l, 1);
//            }
//        }).start();

    }
}
