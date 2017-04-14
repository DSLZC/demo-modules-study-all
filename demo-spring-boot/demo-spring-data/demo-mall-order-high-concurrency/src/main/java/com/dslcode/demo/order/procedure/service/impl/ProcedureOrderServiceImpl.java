package com.dslcode.demo.order.procedure.service.impl;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.demo.order.domain.OrderItem;
import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.procedure.service.ProcedureOrderService;
import com.dslcode.demo.order.repository.GoodsRepository;
import com.dslcode.demo.order.repository.OrderItemRepository;
import com.dslcode.demo.order.repository.SubmitOrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
@Service
public class ProcedureOrderServiceImpl implements ProcedureOrderService {

    @Autowired
    private OrderItemRepository orderRepository;
    @Autowired
    private SubmitOrderItemRepository submitOrderItemRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public SubmitOrderItem add(Long goodsId, Long userId, Integer num) throws Exception {
        int row_num = 0;
        String code = StringUtil.append2String(userId, goodsId, RandomCode.getNumCode(4));
        log.debug("row_num={}", row_num);
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("p_insert_submit_order_item")
                .registerStoredProcedureParameter("goods_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("user_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("num", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("code", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("row_num", Integer.class, ParameterMode.INOUT)
                .setParameter("goods_id", goodsId)
                .setParameter("user_id", userId)
                .setParameter("num", num)
                .setParameter("code", code)
                .setParameter("row_num", row_num);

        storedProcedure.execute();
        row_num = (int) storedProcedure.getOutputParameterValue("row_num");
        log.debug("row_num={}", row_num);

        if (row_num > 0) this.orderRepository.save(new OrderItem(num, goodsId, userId, 1, new Date()));
        return this.submitOrderItemRepository.findByCode(code);

    }

}
