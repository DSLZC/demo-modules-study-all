package com.dslcode.web.pay.dto.wechat;

import lombok.Data;

/**
 * Created by dongsilin on 2016/11/25.
 * 调起微信APP支付参数
 */
@Data
public class PayAppDTO {

    /** 应用ID：微信开放平台审核通过的应用APPID */
    private String appid;
    /** 商户号：微信支付分配的商户号 */
    private String partnerid;
    /** 预支付交易会话ID：微信返回的支付交易会话ID */
    private String prepayid;
    /** 扩展字段：暂填写固定值Sign=WXPay，微信原参数名称为：package，与Java关键字冲突，多加了d */
    private String packaged = "Sign=WXPay";
    /** 随机字符串：随机字符串，不长于32位 */
    private String noncestr;
    /** 时间戳：自1970年1月1日 0点0分0秒以来的秒数 */
    private String timestamp;
    /** 签名：详见签名生成算法 */
    private String sign;

    public PayAppDTO(){}

    public PayAppDTO(String appid, String partnerid, String prepayid, String noncestr, String timestamp, String sign) {
        this.appid = appid;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
