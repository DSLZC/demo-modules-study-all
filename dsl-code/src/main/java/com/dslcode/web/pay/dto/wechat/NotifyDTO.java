package com.dslcode.web.pay.dto.wechat;

import lombok.Data;

/**
 * Created by dongsilin on 2016/12/5.
 */
@Data
public class NotifyDTO {

    /**	返回状态码，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断。例：SUCCESS */
    private	String return_code;

    /**	返回信息，返回信息，如非空，为错误原因 ，签名失败 ，参数格式校验错误。例：签名失败 */
    private	String return_msg;

    /**	应用ID，微信开放平台审核通过的应用APPID。例：wx8888888888888888 */
    private	String appid;

    /**	商户号，微信支付分配的商户号。例：1900000109 */
    private	String mch_id;

    /**	设备号，微信支付分配的终端设备号。例：013467007045764 */
    private	String device_info;

    /**	随机字符串，随机字符串，不长于32位。例：5K8264ILTKCH16CQ2502SI8ZNMTM67VS */
    private	String nonce_str;

    /**	签名，签名，详见签名算法。例：C380BEC2BFD727A4B6845133519F3AD6 */
    private	String sign;

    /**	业务结果，SUCCESS/FAIL。例：SUCCESS */
    private	String result_code;

    /**	错误代码，错误返回的信息描述。例：SYSTEMERROR */
    private	String err_code;

    /**	错误代码描述，错误返回的信息描述。例：系统错误 */
    private	String err_code_des;

    /**	用户标识，用户在商户appid下的唯一标识。例：wxd930ea5d5a258f4f */
    private	String openid;

    /**	是否关注公众账号，用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效。例：Y */
    private	String is_subscribe;

    /**	交易类型，APP。例：APP */
    private	String trade_type;

    /**	付款银行，银行类型，采用字符串类型的银行标识，银行类型见银行列表。例：CMC */
    private	String bank_type;

    /**	总金额，订单总金额，单位为分。例：100 */
    private	String total_fee;

    /**	货币种类，货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型。例：CNY */
    private	String fee_type;

    /**	现金支付金额，现金支付金额订单现金支付金额，详见支付金额。例：100 */
    private	String cash_fee;

    /**	现金支付货币类型，货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型。例：CNY */
    private	String cash_fee_type;

    /**	代金券或立减优惠金额，代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额，详见支付金额。例：10 */
    private	int coupon_fee;

    /**	代金券或立减优惠使用数量，代金券或立减优惠使用数量。例：1 */
    private	String coupon_count;

    /**	代金券或立减优惠ID，代金券或立减优惠ID,$n为下标，从0开始编号。例：10000 */
    private	String coupon_id_$n;

    /**	单个代金券或立减优惠支付金额，单个代金券或立减优惠支付金额,$n为下标，从0开始编号。例：100 */
    private	String coupon_fee_$n;

    /**	微信支付订单号，微信支付订单号。例：1217752501201407033233368018 */
    private	String transaction_id;

    /**	商户订单号，商户系统的订单号，与请求一致。例：1212321211201407033568112322 */
    private	String out_trade_no;

    /**	商家数据包，商家数据包，原样返回。例：123456 */
    private	String attach;

    /**	支付完成时间，支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则。例：2.0141E+13 */
    private	String time_end;

}