package com.dslcode.web.pay.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/11/19.
 */
@Data
public class BasePayDTO {
    public static final Logger log = LoggerFactory.getLogger(BasePayDTO.class);

    /** 应用ID：微信开放平台审核通过的应用APPID */
    private String appid;

    /** 商户号：微信支付分配的商户号 */
    private String mch_id;

    /** API密钥 */
    private String apiKey;

    /** 订单总金额 */
    private BigDecimal totalMoney;

    /** 商品描述 */
    private String describe;

    /** 附加数据：在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 */
    private String attach;

    /** 商户订单号：商户系统内部的订单号,32个字符内、可包含字母 */
    private String out_trade_no;

    /** 通知地址：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 */
    private String notify_url;

    public BasePayDTO(String out_trade_no, BigDecimal totalMoney, String describe, String attach) {
        this.totalMoney = totalMoney;
        this.describe = describe;
        this.attach = attach;
        this.out_trade_no = out_trade_no;
    }

    public BasePayDTO() {
    }
}
