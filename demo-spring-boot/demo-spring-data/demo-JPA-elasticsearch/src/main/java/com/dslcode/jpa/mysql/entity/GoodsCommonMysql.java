package com.dslcode.jpa.mysql.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_goods_common")
public class GoodsCommonMysql implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "goods_commonid")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    @Column(name = "goods_image")
    private String image;

    @Column(name = "goods_state")
    private Integer state; // 商品状态 0下架，1正常，10违规（禁售）

    @Column(name = "goods_price")
    private BigDecimal price; // 商品价格

    @Column(name = "goods_marketprice")
    private BigDecimal marketPrice; // 市场价

    @Column(name = "store_id")
    private Long storeId;

    @Column(name= "goods_salenum")
    private Integer saleNum;

}