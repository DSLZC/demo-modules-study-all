package com.dslcode.web.pay;

import com.dslcode.web.pay.config.PayConfig;
import com.dslcode.web.pay.config.PayEnum;
import com.dslcode.web.pay.dto.BasePayDTO;
import com.dslcode.web.pay.service.WeChatNotify;
import com.dslcode.web.pay.service.WeChatPay;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/11/28.
 * 支付门面
 */
public class PayFacade {


    /**
     * 支付下单
     * @param basePayDTO 基本支付参数
     * @param config 支付基本配置
     * @param payType 支付方式
     * @return payType=weChat_app返回PayAppDTO，payType=weChat_scan返回二维码图片InputStream，payType=weChat_gzh暂未测试通过，因为openid获取不到
     */
    public static Object makeOrder(BasePayDTO basePayDTO, PayConfig config, PayEnum.PayType payType){
        if (payType == PayEnum.PayType.weChat_app || payType == PayEnum.PayType.weChat_gzh || payType == PayEnum.PayType.weChat_scan)
        return new WeChatPay().makeOrder(basePayDTO, config, payType);
        else if (payType == PayEnum.PayType.ali) return null;
        return null;
    }

    /**
     * 支付后台回调处理
     * @param payType 支付类型
     * @param responseStr 回调参数
     * @param weChatNotify 支付成功自定义订单处理业务
     */
    public static void notifyPay(PayEnum.PayType payType, String responseStr, WeChatNotify weChatNotify){
        if (payType == PayEnum.PayType.weChat_app || payType == PayEnum.PayType.weChat_gzh || payType == PayEnum.PayType.weChat_scan)
            new WeChatPay().notifyPay(responseStr, weChatNotify);
        else if (payType == PayEnum.PayType.ali) ;
    }

    /**
     * 申请退款。
     * <p>微信支付申请退款暂未成功，需要使用证书
     * @param basePayDTO
     * @param config
     * @param refundFee
     * @param outRefundNo
     * @param payType
     */
    public static void applyRefund(BasePayDTO basePayDTO, PayConfig config, BigDecimal refundFee, String outRefundNo, PayEnum.PayType payType){
        if (payType == PayEnum.PayType.weChat_app || payType == PayEnum.PayType.weChat_gzh || payType == PayEnum.PayType.weChat_scan){
            new WeChatPay().applyRefund(basePayDTO, config, refundFee, outRefundNo);
        }
    }

}
