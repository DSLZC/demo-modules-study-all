package com.dslcode.web.pay.dto.wechat;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.web.pay.config.PayConfig;
import com.dslcode.web.pay.dto.BasePayDTO;
import com.dslcode.web.pay.util.MD5;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/12/13.
 */
@Data
public class ApplyRefundDTO extends BasePayDTO {

    /** 随机字符串：随机字符串，不长于32位。 */
    private String nonce_str;

    /** 商户退款单号：商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔。 */
    private String out_refund_no;

    /** 签名：详见签名生成算法 */
    private String sign;

    /** 总金额：订单总金额，单位为分 */
    private int total_fee;

    /** 退款金额：退款总金额，订单总金额，单位为分，只能为整数 */
    private int refund_fee;

    /** 退款金额：退款总金额，订单总金额，单位为分，只能为整数 */
    private String op_user_id;


    public ApplyRefundDTO(BasePayDTO basePay, PayConfig config, BigDecimal refundFee, String outRefundNo) {

        this.setAppid(config.getWeChatAppid());
        this.setMch_id(config.getWeChatMchId());
        this.setApiKey(config.getApiKey());

        int totalFee = basePay.getTotalMoney().multiply(new BigDecimal(100)).intValue();
        this.setTotal_fee(totalFee > 0 ? totalFee : 1); //至少一分钱
        int refund_fee = refundFee.multiply(new BigDecimal(100)).intValue();
        this.setRefund_fee(refund_fee > 0 ? refund_fee : 1); //至少一分钱
        this.setOut_trade_no(basePay.getOut_trade_no());
        this.setOut_refund_no(outRefundNo);

        this.setOp_user_id(config.getWeChatMchId());

        this.setNonce_str(StringUtil.append2String(System.currentTimeMillis(), RandomCode.getNumCode(6)));
        this.setSign(getWeChatPaySign(this));
    }

    private static String getWeChatPaySign(ApplyRefundDTO placeOrderDTO){
        StringBuffer sb = StringUtil.append(
                "appid=", placeOrderDTO.getAppid(),
                "&mch_id=", placeOrderDTO.getMch_id(),
                "&nonce_str=", placeOrderDTO.getNonce_str(),
                "&notify_url=", placeOrderDTO.getNotify_url(),
                "&op_user_id=", placeOrderDTO.getOp_user_id(),
                "&out_refund_no=", placeOrderDTO.getOut_refund_no(),
                "&out_trade_no=", placeOrderDTO.getOut_trade_no(),
                "&refund_fee=", placeOrderDTO.getRefund_fee(),
                "&total_fee=", placeOrderDTO.getTotal_fee()
        );
        String signNotMD5 = StringUtil.append2String(sb, "&key=", placeOrderDTO.getApiKey());
        log.debug("signNotMD5 = {}", signNotMD5);
        return MD5.MD5Encode(signNotMD5).toUpperCase();
    }

}
