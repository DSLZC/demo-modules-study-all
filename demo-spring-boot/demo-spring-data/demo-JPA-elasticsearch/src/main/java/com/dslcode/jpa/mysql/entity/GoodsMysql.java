package com.dslcode.jpa.mysql.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dongsilin on 2017/3/10.
 */
@Table(name = "t_goods")
@Entity
@Data
public class GoodsMysql {

    @Id
    @Column(name = "goods_commonid")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    @Column(name = "goods_image")
    private String image;

    @Column(name = "goods_price")
    private Double price;

    @Column(name = "goods_salenum")
    private Integer saleNum = 0;
}
