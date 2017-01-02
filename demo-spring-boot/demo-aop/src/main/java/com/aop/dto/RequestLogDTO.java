package com.aop.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongsilin on 2016/12/30.
 */
@Data
@XmlRootElement
public class RequestLogDTO {

    private Long id;

    /** 创建时间 秒 */
    private long createTime;

    /** 客户端设备名称 */
    private String deviceName;

    /** sessionId */
    private String sessionId;

    /** 完整请求url */
    private String requestUrl;

    /** 访问应用app别名 */
    private String appAlias;

    /** 访问者IP */
    private String remoteIp;

    /** 访问者姓名/电话 */
    private String clientUser;

    /** 访问uuid */
    private String callUuid;

}
