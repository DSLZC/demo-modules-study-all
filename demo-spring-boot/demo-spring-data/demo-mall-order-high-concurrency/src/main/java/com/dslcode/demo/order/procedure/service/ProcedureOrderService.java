package com.dslcode.demo.order.procedure.service;

import com.dslcode.demo.order.domain.SubmitOrderItem;

/**
 * Created by dongsilin on 2017/4/7.
 */
public interface ProcedureOrderService {
    SubmitOrderItem add(Long goodsId, Long userId, Integer num) throws Exception;
}
