package com.dslcode.demo.order.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by dongsilin on 2017/4/7.
 */
@Data
@Table(name = "goods")
@Entity
public class Goods {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int stock;

    private BigDecimal price;
    /**
     * 版本号，每一次扣减库存 version+1
     */
    private Long version = 1L;

    public Goods() {
    }

    public Goods(String name, int stock, BigDecimal price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
}
