package com.dslcode.core.function;

import lombok.Data;

/**
 * 方法调用返回对象，便于封装错误信息，避免抛出异常影响性能
 * @author 董思林
 * @date 2017-07-03
 */
@Data
public class FunctionResult {

    /** 是否操作成功 */
    private boolean success;
    /** 操作说明信息 */
    private String message;
    /** 响应数据 */
    private Object body;

    public FunctionResult(){}

    private FunctionResult(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    /**
     * 处理成功
     * @return
     */
    public static FunctionResult buildSuccess() {
        return new FunctionResult(true, null, null);
    }
    /**
     * 处理成功
     * @param message
     * @return
     */
    public static FunctionResult buildSuccess(String message) {
        return new FunctionResult(true, message, null);
    }
    /**
     * 处理成功
     * @param message
     * @param data
     * @return
     */
    public static FunctionResult buildSuccess(String message, Object data) {
        return new FunctionResult(true, message, data);
    }
    /**
     * 处理成功
     * @param data
     * @return
     */
    public static FunctionResult buildSuccessData(Object data) {
        return new FunctionResult(true, null, data);
    }

    /**
     * 处理失败
     * @return
     */
    public static FunctionResult buildFailed() {
        return new FunctionResult(false, null, null);
    }
    /**
     * 处理失败
     * @param message
     * @return
     */
    public static FunctionResult buildFailed(String message) {
        return new FunctionResult(false, message, null);
    }
    /**
     * 处理失败
     * @param message
     * @param data
     * @return
     */
    public static FunctionResult buildFailed(String message, Object data) {
        return new FunctionResult(false, message, data);
    }
    /**
     * 处理失败
     * @param data
     * @return
     */
    public static FunctionResult buildFailedData(Object data) {
        return new FunctionResult(false, null, data);
    }
    
}
