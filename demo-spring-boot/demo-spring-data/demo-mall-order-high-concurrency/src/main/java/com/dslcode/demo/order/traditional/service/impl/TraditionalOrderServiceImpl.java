package com.dslcode.demo.order.traditional.service.impl;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.demo.order.domain.Goods;
import com.dslcode.demo.order.domain.SubmitOrderItem;
import com.dslcode.demo.order.repository.GoodsRepository;
import com.dslcode.demo.order.repository.OrderItemRepository;
import com.dslcode.demo.order.repository.SubmitOrderItemRepository;
import com.dslcode.demo.order.traditional.service.TraditionalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Slf4j
@Service
public class TraditionalOrderServiceImpl implements TraditionalOrderService {

    @Autowired
    private OrderItemRepository orderRepository;
    @Autowired
    private SubmitOrderItemRepository submitOrderItemRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    @Transactional
    public SubmitOrderItem add(Long goodsId, Long userId, Integer num) throws Exception {
        Goods goods = this.goodsRepository.findOne(goodsId);
        log.debug("-------------------------->{}, stock={}", Thread.currentThread().getName(), goods.getStock());
        SubmitOrderItem submitOrderItem = new SubmitOrderItem(num, goodsId, userId, StringUtil.append2String(userId, goodsId, RandomCode.getNumCode(4)));
        if (goods.getStock() >= num && this.goodsRepository.updateStock(num, goodsId, goods.getVersion()) == 1) {
            submitOrderItem.setSuccess(true);
        } else submitOrderItem.setSuccess(false);

        this.submitOrderItemRepository.save(submitOrderItem);
        return submitOrderItem;

    }

}
