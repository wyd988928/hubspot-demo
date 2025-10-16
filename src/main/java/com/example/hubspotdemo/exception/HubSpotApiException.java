package com.example.hubspotdemo.exception;

/**
 * HubSpot API 异常类，用于封装 HubSpot API 请求过程中的异常
 */
public class HubSpotApiException extends RuntimeException {

    private final int statusCode;
    private final String errorDetails;

    /**
     * 构造函数
     * 
     * @param message 异常消息
     * @param statusCode HTTP 状态码
     * @param errorDetails 错误详情
     */
    public HubSpotApiException(String message, int statusCode, String errorDetails) {
        super(message);
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    /**
     * 构造函数
     * 
     * @param message 异常消息
     * @param cause 异常原因
     */
    public HubSpotApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 500;
        this.errorDetails = cause.getMessage();
    }

    /**
     * 获取 HTTP 状态码
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 获取错误详情
     */
    public String getErrorDetails() {
        return errorDetails;
    }
}