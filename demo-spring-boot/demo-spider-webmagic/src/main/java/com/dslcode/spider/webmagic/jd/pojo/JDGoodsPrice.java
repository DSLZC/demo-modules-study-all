package com.dslcode.spider.webmagic.jd.pojo;

import lombok.Data;

/**
 * Created by dongsilin on 2017/6/6.
 */
@Data
public class JDGoodsPrice {

    private String id;
    private float p;
    private float m;
    private float op;
    private float tpp;

    public int getPrice(){
        return (int) (this.p * 100);
    }
    public int getOldPrice(){
        return (int) (this.op * 100);
    }


}
