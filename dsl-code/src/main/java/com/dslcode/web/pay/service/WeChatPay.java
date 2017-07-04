package com.dslcode.web.pay.service;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.file.ImageUtil;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.string.XMLUtil;
import com.dslcode.core.util.EqualsUtil;
import com.dslcode.core.util.NullUtil;
import com.dslcode.web.pay.config.PayConfig;
import com.dslcode.web.pay.config.PayEnum;
import com.dslcode.web.pay.dto.BasePayDTO;
import com.dslcode.web.pay.dto.wechat.ApplyRefundDTO;
import com.dslcode.web.pay.dto.wechat.NotifyDTO;
import com.dslcode.web.pay.dto.wechat.PayAppDTO;
import com.dslcode.web.pay.dto.wechat.UnityPlaceOrderDTO;
import com.dslcode.web.request.create.RestTemplateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by dongsilin on 2016/11/19.
 */
public class WeChatPay implements PayMethod{
    public static final Logger log = LoggerFactory.getLogger(BasePayDTO.class);

    /**
     * 微信支付统一下单
     * @param basePay 基本支付参数
     * @param config 支付基本配置
     * @param payType 支付类型
     * @return payType=weChat_app返回PayAppDTO  payType=weChat_scan返回二维码图片InputStream   payType=weChat_gzh暂未测试通过
     */
    @Override
    public Object makeOrder(BasePayDTO basePay, PayConfig config, PayEnum.PayType payType) {
        // 封装统一下单DTO
        UnityPlaceOrderDTO placeOrderDTO = new UnityPlaceOrderDTO(basePay, config, payType);
        try {

            // 统一下单请求xml参数
            String requestXml = getUnifyOrderXml(placeOrderDTO, payType);
            // 执行统一下单
            String unifyPlaceOrderReturnXml = unifyPlaceOrder(requestXml, config.getWeChatGateWayUrl());
            if(NullUtil.isNull(unifyPlaceOrderReturnXml)) throw new Exception("统一下单 请求失败......");
            // 返回xml解析
            Map<String, Object> unifyPlaceOrderReturnMap = XMLUtil.xmlParse2Map(unifyPlaceOrderReturnXml);
            // 封装 调起微信APP支付参数
            if(EqualsUtil.equalsAllIgnoreCase("SUCCESS", String.valueOf(unifyPlaceOrderReturnMap.get("return_code")), String.valueOf(unifyPlaceOrderReturnMap.get("result_code")))){
                if(payType == PayEnum.PayType.weChat_app || payType == PayEnum.PayType.weChat_gzh){
                    // 返回PayAppDTO给APP
                    return new PayAppDTO(
                            placeOrderDTO.getAppid(),
                            placeOrderDTO.getMch_id(),
                            String.valueOf(unifyPlaceOrderReturnMap.get("prepay_id")),
                            placeOrderDTO.getNonce_str(),
                            String.valueOf(DateUtil.nowSeconds()),
                            placeOrderDTO.getSign()
                    );
                } else if (payType == PayEnum.PayType.weChat_scan){
                    // 返回二维码图片InputStream
                    return ImageUtil.QRCode(String.valueOf(unifyPlaceOrderReturnMap.get("code_url")), null);
                }
            }
            throw new Exception(String.valueOf(null==unifyPlaceOrderReturnMap.get("err_code_des")? unifyPlaceOrderReturnMap.get("return_msg") : unifyPlaceOrderReturnMap.get("err_code_des")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 微信支付回调，解析xml回调参数，以及调起自定义订单处理业务
     * @param notifyXML xml回调参数
     * @param weChatNotify 自定义订单处理业务
     */
    @Override
    public void notifyPay(String notifyXML, WeChatNotify weChatNotify) {

        NotifyDTO notifyDTO = XMLUtil.xmlParse2Instance(notifyXML, NotifyDTO.class);
        if(null != notifyDTO && "SUCCESS".equalsIgnoreCase(notifyDTO.getResult_code())) {
            weChatNotify.invoke(notifyDTO);
        }else {
            try {
                throw new Exception(notifyDTO.getReturn_msg());
            } catch (Exception e) {
                log.debug("{}", e);
            }
        }
    }

    @Override
    public Object pageReturn() {
        return null;
    }

    /**
     * 微信支付申请退款，暂未成功需要使用证书
     * @param basePay
     * @param config
     * @param refundFee
     * @param outRefundNo
     */
    @Override
    public void applyRefund(BasePayDTO basePay, PayConfig config, BigDecimal refundFee, String outRefundNo) {
        ApplyRefundDTO applyRefundDTO = new ApplyRefundDTO(basePay, config, refundFee, outRefundNo);
        // 退款请求xml参数
        String requestXml = getRefundXml(applyRefundDTO);
        // 执行退款请求
        String refundReturnXml = unifyPlaceOrder(requestXml, config.getWeChatRefundGateWayUrl());

        log.debug("refundReturnXml={}", refundReturnXml);

    }

    /**
     * 微信APP支付之前，统一下单
     * @param requestXml xml组装的请求参数
     * @param gateWayUrl 微信支付统一下单地址
     * @return 统一下单返回的xml
     */
    private static String unifyPlaceOrder(String requestXml, String gateWayUrl){

        log.debug("requestXml={}", requestXml);
        // 发送请求
        ResponseEntity<String> res = RestTemplateRequest.postForEntity(
                gateWayUrl,
                new HttpEntity<String>(requestXml),
                String.class,
                null);
        if(res.getStatusCode() == HttpStatus.OK && res.hasBody()) return res.getBody();
        return null;
    }

    /**
     * 生成微信统一下单请求xml参数
     * @param placeOrderDTO
     * @return
     */
    private static String getUnifyOrderXml(UnityPlaceOrderDTO placeOrderDTO, PayEnum.PayType payType) {
        return StringUtil.append2String(
                "<xml>",
                "<appid>", placeOrderDTO.getAppid(), "</appid>",
                NullUtil.isNotNull(placeOrderDTO.getAttach())? "<attach>"+placeOrderDTO.getAttach()+"</attach>" : "",
                "<body>", placeOrderDTO.getBody(), "</body>",
                "<mch_id>", placeOrderDTO.getMch_id(), "</mch_id>",
                "<nonce_str>", placeOrderDTO.getNonce_str(), "</nonce_str>",
                "<notify_url>", placeOrderDTO.getNotify_url(), "</notify_url>",
                payType == PayEnum.PayType.weChat_gzh? "<openid>"+placeOrderDTO.getOpenid()+"</openid>" : "",
                "<out_trade_no>", placeOrderDTO.getOut_trade_no(), "</out_trade_no>",
                payType == PayEnum.PayType.weChat_scan ?"<product_id>"+placeOrderDTO.getProduct_id()+"</product_id>" : "",
                "<spbill_create_ip>", placeOrderDTO.getSpbill_create_ip(), "</spbill_create_ip>",
                "<total_fee>", placeOrderDTO.getTotal_fee(), "</total_fee>",
                "<trade_type>", placeOrderDTO.getTrade_type(),"</trade_type>",
                "<sign>", placeOrderDTO.getSign(), "</sign>",
                "</xml> "
        );
    }

    private static String getRefundXml(ApplyRefundDTO applyRefundDTO) {
        return StringUtil.append2String(
            "<xml>",
                "<appid>", applyRefundDTO.getAppid(), "</appid>",
                "<mch_id>", applyRefundDTO.getMch_id(), "</mch_id>",
                "<nonce_str>", applyRefundDTO.getNonce_str(), "</nonce_str>",
                "<op_user_id>", applyRefundDTO.getOp_user_id(), "</op_user_id>",
                "<out_refund_no>", applyRefundDTO.getOut_refund_no(), "</out_refund_no>",
                "<out_trade_no>", applyRefundDTO.getOut_trade_no(), "</out_trade_no>",
                "<refund_fee>", applyRefundDTO.getRefund_fee(), "</refund_fee>",
                "<total_fee>", applyRefundDTO.getTotal_fee(), "</total_fee>",
                "<sign>", applyRefundDTO.getSign(), "</sign>",
            "</xml> "
        );
    }
}
