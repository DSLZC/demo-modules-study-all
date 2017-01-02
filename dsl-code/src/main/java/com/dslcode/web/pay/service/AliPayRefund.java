package com.dslcode.web.pay.service;

import com.dslcode.core.json.JsonUtil;
import com.dslcode.core.map.DMap;
import com.dslcode.core.string.StringUtil;
import com.dslcode.web.pay.dto.ali.CommonPayDTO;
import com.dslcode.web.pay.dto.ali.RefundDTO;
import com.dslcode.web.pay.dto.ali.RefundResponseDTO;
import com.dslcode.web.pay.util.RSA;
import com.dslcode.web.request.create.RestTemplateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dongsilin on 2016/12/1.
 */
public class AliPayRefund {

    private static final Logger log = LoggerFactory.getLogger(AliPayRefund.class);

    public static RefundResponseDTO invoke(RefundDTO refund, String appId, String gateWay, String privateKey, String publicKey) throws Exception{
        gateWay = gateWay.contains("?")? gateWay : gateWay+"?";// 末尾是否含有？
        CommonPayDTO commonPay = CommonPayDTO.init(appId, refund);
        commonPay.setSign(createRSASign(commonPay, privateKey));

        String param = dto2UrlParams(commonPay);
        log.debug("param={}", gateWay+param);

        String biz_content = RefundDTO.toJson((RefundDTO)commonPay.getBiz_content());
        log.debug("biz_content={}", biz_content);

        String res = RestTemplateRequest.postForObject(gateWay+param, null, String.class, new DMap<String>("biz_content", biz_content).getMap());
        log.debug("res={}", res);

        RefundResponseDTO refundResponseDTO = JsonUtil.readJson(res, RefundResponseDTO.class);
        log.debug("refundResponseDTO={}", refundResponseDTO);
        // 准备验签
        String refundResponseStr = res.substring(res.indexOf(":{\"")+1, res.indexOf("\"}")+2);
        if (refundResponseDTO.getAlipay_trade_refund_response().getCode() == CommonPayDTO.AliPayResCode.success.value){
            if (RSA.verify(refundResponseStr, refundResponseDTO.getSign(), publicKey)){
                if (refundResponseDTO.getAlipay_trade_refund_response().getFund_change().equals("Y")) return refundResponseDTO;
                else throw new Exception("支付宝退款 ：本次退款资金未发生变化...");
            } else throw new Exception("支付宝退款 ：返回参数验签失败...");
        }else throw new Exception("支付宝退款 ：请求退款失败...");
    }

    private static String createSign(CommonPayDTO commonDTO) {
        return StringUtil.append2String(
                "app_id=" +commonDTO.getApp_id(),
                "&biz_content=" +RefundDTO.toJson((RefundDTO) commonDTO.getBiz_content()),
                "&charset=" +commonDTO.getCharset(),
                "&method=" +commonDTO.getMethod(),
                "&sign_type=" +commonDTO.getSign_type(),
                "&timestamp=" +commonDTO.getTimestamp(),
                "&version=" +commonDTO.getVersion()
        );
    }

    private static String createRSASign(CommonPayDTO commonDTO, String privateKey) {
        return RSA.RSAEncode(createSign(commonDTO), privateKey);
    }

    private static String dto2UrlParams(CommonPayDTO commonDTO){
        return StringUtil.append2String(
                "app_id=" +commonDTO.getApp_id(),
                "&biz_content={biz_content}",
                "&charset=" +commonDTO.getCharset(),
                "&method=" +commonDTO.getMethod(),
                "&sign_type=" +commonDTO.getSign_type(),
                "&timestamp=" +commonDTO.getTimestamp(),
                "&version=" +commonDTO.getVersion(),
                "&sign=" +commonDTO.getSign()
        );
    }

}
