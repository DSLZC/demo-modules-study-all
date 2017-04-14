package com.dslcode.demo.order.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Data
@Table(name = "submit_order_item")
@Entity
public class SubmitOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int num;

    private Long goodsId;

    private Long userId;

    private String code;

    private Boolean success;

    public SubmitOrderItem(int num, Long goodsId, Long userId, String code) {
        this.num = num;
        this.goodsId = goodsId;
        this.userId = userId;
        this.code = code;
    }

    public SubmitOrderItem() {
    }
}
