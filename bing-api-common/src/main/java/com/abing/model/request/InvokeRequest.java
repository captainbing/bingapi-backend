package com.abing.model.request;

import lombok.Data;

import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:19
 * @Description
 */
@Data
public class InvokeRequest {

    /**
     * 请求URL
     */
    private String url;
    /**
     * 请求方式 GET POST ...
     */
    private String method;
    /**
     * 请求头
     */
    private Map<String, String> requestParams;
    /**
     * 请求头
     */
    private Map<String, String> requestHeaders;
    /**
     * 响应头
     */
    private Map<String, String> responseHeaders;

    /**
     * 请求体 JSON
     */
    private String requestBody;
    /**
     * 响应体 JSON
     */
    private String responseBody;
    /**
     * test响应
     */
    private String baseResponse;

}
