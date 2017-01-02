package com.dslcode.web.pay.service;

import com.dslcode.web.pay.dto.wechat.NotifyDTO;

/**
 * Created by dongsilin on 2016/12/9.
 * <p>
 * 微信支付成功回调订单处理，函数式接口，Java8 lambda或使用匿名类调用
 * <p>
 * 注：该接口下只有唯一的invoke方法
 */
public interface WeChatNotify {

    /**
     * 自定义的回调订单处理业务，
     * @param notifyDTO
     */
    void invoke(NotifyDTO notifyDTO);
}
