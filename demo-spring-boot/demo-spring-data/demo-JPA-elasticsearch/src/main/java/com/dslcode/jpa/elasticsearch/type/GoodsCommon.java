package com.dslcode.jpa.elasticsearch.type;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;

@Data
@Document(indexName = "51ishare",type = "goodscommon"/*, shards = 1, replicas = 0, refreshInterval = "-1"*/)
public class GoodsCommon {

    @Id
    @Field
    private Long id;

    @Field
    private String name;
    @Field
    private String image;
    @Field
    private Integer state; // 商品状态 0下架，1正常，10违规（禁售）
    @Field
    private BigDecimal price; // 商品价格
    @Field
    private BigDecimal marketPrice; // 市场价
    @Field
    private Long storeId;
    @Field
    private Integer saleNum;

}