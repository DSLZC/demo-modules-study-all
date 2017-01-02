package com.dslcode.web.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * AJAX请求的返回对象
 * @author 董思林
 * @date 2016-07-13
 */
@JsonAutoDetect
@Data
public class WebResponse<T> implements Serializable {

	/** 响应状态  200成功  404请求路径错误  500服务器报错 */
	private int status;
    /** 是否操作成功 */
    private boolean success;
    /** 操作信息 */
    private String message;
    /** 响应数据 */
    private T body;

    public WebResponse(){}

    private WebResponse(int status, boolean success, String message, T body) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public static WebResponse buildSuccess() {
        return new WebResponse(HttpStatus.OK.value(), true, null, null);
    }

    public static WebResponse buildSuccessMsg(String message) {
        return new WebResponse(HttpStatus.OK.value(), true, message, null);
    }

    public static WebResponse buildSuccessData(Object data) {
        return new WebResponse(HttpStatus.OK.value(), true, null, data);
    }

    public static WebResponse buildSuccess(String message, Object data) {
        return new WebResponse(HttpStatus.OK.value(), true, message, data);
    }
    public static WebResponse buildError() {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, null, null);
    }

    public static WebResponse buildErrorMsg(String message) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, null);
    }

    public static WebResponse buildErrorData(Object data) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, null, data);
    }

    public static WebResponse buildError(String message, Object data) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, data);
    }

}
