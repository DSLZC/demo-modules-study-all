package com.dslcode.spider.webmagic.jd.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dongsilin on 2017/6/7.
 */
@Data
@Entity
@Table(name = "jd_goods_common")
public class JdGoodsCommon {

    @Id
    @GeneratedValue
    private Long id;

    private long mainSkuId;

    private long skuId;

    private String cat;

    private String title;

    private String mainImg;

    private int price;

    private int oldPrice;

    public JdGoodsCommon() {
    }
    public JdGoodsCommon(JdGoods jdGoods) {
        this.mainImg = jdGoods.getMainImg();
        this.cat = jdGoods.getCat();
        this.mainSkuId = jdGoods.getMainSkuId();
        this.skuId = jdGoods.getSkuId();
        this.price = jdGoods.getPrice();
        this.oldPrice = jdGoods.getOldPrice();
        String title = jdGoods.getTitle();
        for (String v : jdGoods.getAttrs().values()) title = title.replace(v, "");
        this.title = title;

    }

}
