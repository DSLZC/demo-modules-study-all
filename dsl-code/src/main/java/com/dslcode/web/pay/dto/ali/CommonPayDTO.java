package com.dslcode.web.pay.dto.ali;

import com.dslcode.core.date.DateUtil;
import lombok.Data;

/**
 * Created by dongsilin on 2016/12/1.
 * 支付宝 公共请求参数
 */
@Data
public class CommonPayDTO {

    /** 支付宝分配给开发者的应用ID，如：2014072300007148 */
    private String app_id;

    /** 接口名称，如：alipay.trade.refund */
    private String method;

    /** 请求使用的编码格式，如utf-8,gbk,gb2312等 */
    private String charset = "utf-8";

    /** 商户生成签名字符串所使用的签名算法类型，目前支持RSA */
    private String sign_type = "RSA";

    /** 商户请求参数的签名串 */
    private String sign;

    /** 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss */
    private String timestamp;

    /** 调用的接口版本，固定为：1.0 */
    private String version = "1.0";

    /** 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档 */
    private Object biz_content;

    public CommonPayDTO(){}

    public static CommonPayDTO init(String appId, Object bizContent){
        CommonPayDTO commonPay = new CommonPayDTO();
        commonPay.setApp_id(appId);
        commonPay.setBiz_content(bizContent);
        commonPay.setMethod(AliPayMethod.refund.value);
        commonPay.setTimestamp(DateUtil.nowStr(DateUtil.yyyyMMddHHmmss));
        return commonPay;
    }

    public enum AliPayMethod{
        /** 支付 */
        pay("alipay.trade.pay"),
        /** WAP支付 */
        pay_wap("alipay.trade.wap.pay"),
        /** 支付结果查询 */
        pay_query("alipay.trade.query"),
        /** 退款 */
        refund("alipay.trade.refund"),
        /** 退款查询 */
        refund_query("alipay.trade.refund.query");

        public String value;
        AliPayMethod(String value){this.value = value;}
    }

    /**
     * 支付宝处理返回code
     */
    public enum AliPayResCode{
        /** 业务处理成功 */
        success(10000);

        public int value;
        AliPayResCode(int value){this.value = value;}
    }
}