package com.dslcode.web.pay.dto.wechat;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;
import com.dslcode.web.pay.config.PayConfig;
import com.dslcode.web.pay.config.PayEnum;
import com.dslcode.web.pay.dto.BasePayDTO;
import com.dslcode.web.pay.util.MD5;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/11/19.
 * 微信 统一下单DTO， 封装支付参数
 * @author 董思林
 * @date 2016-11-19 10:05:00
 */
@Data
public class UnityPlaceOrderDTO extends BasePayDTO {

    /** 随机字符串：随机字符串，不长于32位。 */
    private String nonce_str;

    /** 签名：详见签名生成算法 */
    private String sign;

    /** 商品描述：应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。 */
    private String body;

    /** 总金额：订单总金额，单位为分 */
    private int total_fee;

    /** 终端IP：用户端实际ip，123.12.12.123 */
    private String spbill_create_ip;

    /** 支付类型: APP or JSAPI or NATIVE */
    private String trade_type;

    /** 商品id: trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。 */
    private String product_id = "111111111";

    /** 用户标识: trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。 */
    private String openid = "";


    public UnityPlaceOrderDTO() {}
    public UnityPlaceOrderDTO(BasePayDTO basePay, PayConfig config, PayEnum.PayType payType) {
        this.setAppid(config.getWeChatAppid());
        this.setOpenid(config.getWeChatOpenid());
        this.setMch_id(config.getWeChatMchId());
        this.setApiKey(config.getApiKey());
        this.setNotify_url(config.getWeChatNotifyUrl());
        this.setSpbill_create_ip(config.getWeChatSpbillCreateIp());

        int totalFee = basePay.getTotalMoney().multiply(new BigDecimal(100)).intValue();
        this.setTotal_fee(totalFee > 0 ? totalFee : 1); //至少一分钱
        this.setBody(basePay.getDescribe());
        this.setAttach(basePay.getAttach());
        this.setOut_trade_no(basePay.getOut_trade_no());

        this.setNonce_str(StringUtil.append2String(System.currentTimeMillis(), RandomCode.getNumCode(6)));
        this.setTrade_type(payType == PayEnum.PayType.weChat_app? "APP" : payType == PayEnum.PayType.weChat_gzh? "JSAPI": "NATIVE");
        this.setSign(getWeChatPaySign(this, payType));
    }

    /**
     * 生成微信支付签名
     * ◆ 参数名ASCII码从小到大排序（字典序）；
     * ◆ 如果参数的值为空不参与签名；
     * ◆ 参数名区分大小写；
     * ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
     * ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
     * appid=wxb841267a49431634
     * &body=苹果
     * &mch_id=1285329501
     * &nonce_str=767b2cc82cecc0385fe6f1086dd2c748
     * &notify_url=${we.chat.qj.pay.notify.url}
     * &out_trade_no=6114800412377683412528
     * &spbill_create_ip=127.0.0.1
     * &total_fee=200
     * &trade_type=APP
     * &key=944d140ca75572f8b21bce18d6e8e7a3
     * @param placeOrderDTO
     * @return
     */
    private static String getWeChatPaySign(UnityPlaceOrderDTO placeOrderDTO, PayEnum.PayType payType){
        StringBuffer sb = StringUtil.append(
                "appid=", placeOrderDTO.getAppid(),
                NullUtil.isNotNull(placeOrderDTO.getAttach())? "&attach="+placeOrderDTO.getAttach() : "", // 附加参数
                "&body=", placeOrderDTO.getBody(),
                "&mch_id=", placeOrderDTO.getMch_id(),
                "&nonce_str=", placeOrderDTO.getNonce_str(),
                "&notify_url=", placeOrderDTO.getNotify_url(),
                payType == PayEnum.PayType.weChat_gzh? "&openid="+placeOrderDTO.getOpenid() : "",
                "&out_trade_no=", placeOrderDTO.getOut_trade_no(),
                payType == PayEnum.PayType.weChat_scan? "&product_id="+placeOrderDTO.getProduct_id() : "",
                "&spbill_create_ip=", placeOrderDTO.getSpbill_create_ip(),
                "&total_fee=", placeOrderDTO.getTotal_fee(),
                "&trade_type=", placeOrderDTO.getTrade_type()
        );
        String signNotMD5 = StringUtil.append2String(sb, "&key=", placeOrderDTO.getApiKey());
        log.debug("signNotMD5 = {}", signNotMD5);
        return MD5.MD5Encode(signNotMD5).toUpperCase();
    }

}
