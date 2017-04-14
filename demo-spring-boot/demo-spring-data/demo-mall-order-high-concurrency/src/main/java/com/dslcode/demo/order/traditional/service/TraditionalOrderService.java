package com.dslcode.demo.order.traditional.service;

import com.dslcode.demo.order.domain.SubmitOrderItem;

/**
 * Created by dongsilin on 2017/4/7.
 */
public interface TraditionalOrderService {
    SubmitOrderItem add(Long goodsId, Long userId, Integer num) throws Exception;
}
