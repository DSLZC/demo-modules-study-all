package com.dslcode.web.pay.service;

import com.dslcode.web.pay.config.PayConfig;
import com.dslcode.web.pay.config.PayEnum;
import com.dslcode.web.pay.dto.BasePayDTO;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/11/19.
 */
public interface PayMethod {

    /**
     * 调起支付接口，下单
     * @return
     */
    Object makeOrder(BasePayDTO basePay, PayConfig config, PayEnum.PayType payType);

    /**
     * 支付方后台回调
     * @param notifyXML
     * @param weChatNotify
     */
    void notifyPay(String notifyXML, WeChatNotify weChatNotify);

    /**
     * 支付后前台页面返回
     * @return
     */
    Object pageReturn();

    void applyRefund(BasePayDTO basePay, PayConfig config, BigDecimal refundFee, String outRefundNo);

}
