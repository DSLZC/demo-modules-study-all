package com.dslcode.web.pay.config;

public interface PayEnum{

    /**
     * Created by dongsilin on 2016/11/19.
     * 支付类型
     */
    enum PayType{
        /** 支付宝支付 */
        ali(0),
        /** 微信APP支付 */
        weChat_app(3),
        /** 微信公众号支付 */
        weChat_gzh(4),
        /** 微信扫码支付 */
        weChat_scan(5);

        public int value;
        PayType(int value) {
            this.value = value;
        }
    }

    /**
     * Created by dongsilin on 2016/11/19.
     * 支付业务类型需自定义
     */
    enum ServiceType{
        /** 购买商品 */
        buy_goods(0),
        /** 充值vip */
        vip_user(1),
        /** 缴费 */
        charge_fee(2);

        public int value;
        ServiceType(int value) {
            this.value = value;
        }
    }


}

