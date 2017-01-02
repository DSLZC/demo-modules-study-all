package com.dslcode.web.pay.dto.ali;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/12/2.
 * 支付宝 退款响应参数DTO
 */
@Data
public class RefundResponseDTO {

    /** 响应参数 */
    private RefundResponse alipay_trade_refund_response;

    /** 签名 */
    private String sign;

    /**
     * 响应参数
     */
    @Data
    public class RefundResponse{
        /** 网关返回码 */
        private int code;
        /** 明细返回码,网关返回码描述 */
        private String sub_code;

        /** 网关返回码描述 */
        private String msg;

        /** 业务返回码描述, */
        private String sub_msg;

        /** 用户的登录id */
        private String buyer_logon_id;

        /** 买家在支付宝的用户id */
        private String buyer_user_id;

        /** 本次退款是否发生了资金变化 */
        private String fund_change;

        /** 退款支付时间 */
        private String gmt_refund_pay;

        private String open_id;

        /** 商户订单号 */
        private String out_trade_no;

        /** 退款总金额 */
        private BigDecimal refund_fee;

        /** 本次商户实际退回金额 */
        private BigDecimal send_back_fee;

        /** 支付宝交易号 */
        private String trade_no;
    }

}
