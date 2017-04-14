package com.dslcode.demo.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Data
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int num;

    private Long goodsId;

    private Long userId;

    private int state;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date addTime;

    public OrderItem(int num, Long goodsId, Long userId, int state, Date addTime) {
        this.num = num;
        this.goodsId = goodsId;
        this.userId = userId;
        this.state = state;
        this.addTime = addTime;
    }

    public OrderItem() {
    }
}
