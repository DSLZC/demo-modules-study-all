package com.dslcode.sessoin.redis.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dongsilin on 2017/1/13.
 */
@Data
public class WXMemberInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String sessionId = "2968ldjimns715587445";
    private String openid = "ds5db28jt4sd58b1t8j4n";
    private String sessionKey = "sd5bf8j2gjy8km23a3sdf45dsb1fd";
    private Long memberId = 265812L;
    private Long wxMemberId = 2358555L;
    private String channelAppId = "ishare";
    private Long channelId = 3258684L;

    public WXMemberInfo() {}

    public WXMemberInfo(String openid, String sessionKey, Long memberId, Long wxMemberId, String channelAppId, Long channelId) {
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.memberId = memberId;
        this.wxMemberId = wxMemberId;
        this.channelAppId = channelAppId;
        this.channelId = channelId;
    }

    public WXMemberInfo(String channelAppId, Long channelId, String sessionId) {
        this.channelAppId = channelAppId;
        this.channelId = channelId;
        this.sessionId = sessionId;
    }

    public void setMemberInfo(String openid, String sessionKey, Long memberId, Long wxMemberId) {
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.memberId = memberId;
        this.wxMemberId = wxMemberId;
    }

}
