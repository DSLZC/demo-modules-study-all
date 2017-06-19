package com.dslcode.spider.webmagic.jd.db;

import com.dslcode.core.string.StringUtil;
import com.dslcode.spider.webmagic.jd.db.convert.JDGoodsAttributeConvert;
import com.dslcode.spider.webmagic.jd.pojo.JDGoodsPrice;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by dongsilin on 2017/6/7.
 */
@Data
@Entity
@Table(name = "jd_goods")
public class JdGoods{

    @Id
    @GeneratedValue
    private Long id;

    private long goodsCommonId;

    private long mainSkuId;

    private long skuId;

    private String cat;

    private String title;

    private String mainImg;

    private String imgs;

    @Convert(converter = JDGoodsAttributeConvert.class)
    private Map<String, String> attrs;

    private int price;

    private int oldPrice;

    private boolean hasStock;

    private String body;

    public JdGoods() {
    }

    public JdGoods(Object mainSkuId, String skuId, Object cat, String title, String mainImg, List<String> imgs, Map<String, String> attrs, JDGoodsPrice goodsPrice, boolean hasStock, String body) {
        this.mainSkuId = Long.parseLong((String) mainSkuId);
        this.skuId = Long.parseLong(skuId);
        this.cat = StringUtil.join((Collection) cat, ",");
        this.title = title;
        this.mainImg = mainImg;
        this.imgs = StringUtil.join(imgs, ",");
        this.attrs = attrs;
        this.price = goodsPrice.getPrice();
        this.oldPrice = goodsPrice.getOldPrice();
        this.hasStock = hasStock;
        this.body = body;
    }
}
