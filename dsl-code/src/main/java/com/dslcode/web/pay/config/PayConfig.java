package com.dslcode.web.pay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by dongsilin on 2016/11/19.
 */
@Data
@Component
public class PayConfig {

    /** ----------------------微信支付基本参数------------------------- */

    /** 微信开放平台支付url */
    @Value("${pay.weChat.gateWayUrl}")
    private String weChatGateWayUrl;

    /** 微信开放平台申请退款url */
    @Value("${pay.weChat.refund.gateWayUrl}")
    private String weChatRefundGateWayUrl;

    /** 应用ID：微信开放平台审核通过的应用APPID */
    @Value("${pay.weChat.appid}")
    private String weChatAppid;

    /** 用户微信的openid，当trade_type为JSAPI的时候，该属性字段必须设置  */
    @Value("${pay.weChat.openid}")
    private String weChatOpenid;

    /** API密钥 */
    @Value("${pay.weChat.apiKey}")
    private String apiKey;

    /** 商户号：微信支付分配的商户号 */
    @Value("${pay.weChat.mchid}")
    private String weChatMchId;

    /** 终端IP：用户端实际ip，123.12.12.123 */
    @Value("${pay.weChat.spbillCreateIp}")
    private String weChatSpbillCreateIp;

    /** 通知地址：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 */
    @Value("${pay.weChat.notifyUrl}")
    private String weChatNotifyUrl;

    /** --------------------------------------------------------------- */


    /** ----------------------支付宝支付基本参数------------------------- */

    /** --------------------------------------------------------------- */


}
