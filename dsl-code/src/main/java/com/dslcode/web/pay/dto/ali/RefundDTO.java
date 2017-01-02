package com.dslcode.web.pay.dto.ali;

import com.dslcode.core.string.StringUtil;
import lombok.Data;

/**
 * Created by dongsilin on 2016/12/1.
 * 支付宝 退款DTO
 */
@Data
public class RefundDTO {

    /** 订单支付时传入的商户订单号,不能和 trade_no同时为空，如：20150320010101001 */
    private String out_trade_no;

    /** 支付宝交易号，和商户订单号不能同时为空，如：2014112611001004680073956707 */
    private String trade_no;

    /** 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数 */
    private String refund_amount;

    /** 退款的原因说明 */
    private String refund_reason;

    public RefundDTO(String out_trade_no, String trade_no, String refund_amount, String refund_reason) {
        this.out_trade_no = out_trade_no;
        this.trade_no = trade_no;
        this.refund_amount = refund_amount;
        this.refund_reason = refund_reason;
    }

    public RefundDTO() {
    }

    public static String toJson(RefundDTO dto){
        return StringUtil.append2String(
                "{",
                "\"out_trade_no\":\""+dto.getOut_trade_no()+"\",",
                "\"refund_amount\":\""+dto.getRefund_amount()+"\",",
                "\"refund_reason\":\""+dto.getRefund_reason()+"\",",
                "\"trade_no\":\""+dto.getTrade_no()+"\"",
                "}"
        );
    }
}
