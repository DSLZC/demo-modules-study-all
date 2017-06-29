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

	/** 响应状态  200成功 401未认证 403未授权 404请求路径错误  500服务器报错 */
	private int status;
    /** 是否操作成功 */
    private boolean success;
    /** 操作说明信息 */
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

    /**
     * 请求处理成功
     * @return
     */
    public static WebResponse buildSuccess() {
        return new WebResponse(HttpStatus.OK.value(), true, null, null);
    }
    /**
     * 请求处理成功
     * @param message
     * @return
     */
    public static WebResponse buildSuccess(String message) {
        return new WebResponse(HttpStatus.OK.value(), true, message, null);
    }
    /**
     * 请求处理成功
     * @param message
     * @param data
     * @return
     */
    public static WebResponse buildSuccess(String message, Object data) {
        return new WebResponse(HttpStatus.OK.value(), true, message, data);
    }
    /**
     * 请求处理成功
     * @param data
     * @return
     */
    public static WebResponse buildSuccessData(Object data) {
        return new WebResponse(HttpStatus.OK.value(), true, null, data);
    }

    /**
     * 请求处理失败(非系统原因)
     * @return
     */
    public static WebResponse buildFailed() {
        return new WebResponse(HttpStatus.OK.value(), false, null, null);
    }
    /**
     * 请求处理失败(非系统原因)
     * @param message
     * @return
     */
    public static WebResponse buildFailed(String message) {
        return new WebResponse(HttpStatus.OK.value(), false, message, null);
    }
    /**
     * 请求处理失败(非系统原因)
     * @param message
     * @param data
     * @return
     */
    public static WebResponse buildFailed(String message, Object data) {
        return new WebResponse(HttpStatus.OK.value(), false, message, data);
    }
    /**
     * 请求处理失败(非系统原因)
     * @param data
     * @return
     */
    public static WebResponse buildFailedData(Object data) {
        return new WebResponse(HttpStatus.OK.value(), false, null, data);
    }

    /**
     * 服务器内部错误
     * @return
     */
    public static WebResponse buildError() {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, null, null);
    }
    /**
     * 服务器内部错误
     * @param message
     * @return
     */
    public static WebResponse buildError(String message) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, null);
    }
    /**
     * 服务器内部错误
     * @param message
     * @param data
     * @return
     */
    public static WebResponse buildError(String message, Object data) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, data);
    }
    /**
     * 服务器内部错误
     * @param data
     * @return
     */
    public static WebResponse buildErrorData(Object data) {
        return new WebResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, null, data);
    }

    /**
     * 没认证(登录)
     * @return
     */
    public static WebResponse buildUnAuthenticated() {
        return new WebResponse(HttpStatus.UNAUTHORIZED.value(), false, "登录失效，请重新登录", null);
    }
    /**
     * 没认证(登录)
     * @param message
     * @return
     */
    public static WebResponse buildUnAuthenticated(String message) {
        return new WebResponse(HttpStatus.UNAUTHORIZED.value(), false, message, null);
    }

    /**
     * 没授权(没有权限访问)
     * @return
     */
    public static WebResponse buildUnAuthorized() {
        return new WebResponse(HttpStatus.FORBIDDEN.value(), false, "对不起，您没有权限访问", null);
    }
    /**
     * 没授权(没有权限访问)
     * @param message
     * @return
     */
    public static WebResponse buildUnauthorized(String message) {
        return new WebResponse(HttpStatus.FORBIDDEN.value(), false, message, null);
    }


}
