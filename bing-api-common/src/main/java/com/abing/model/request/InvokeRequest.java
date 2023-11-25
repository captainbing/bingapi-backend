package com.abing.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:19
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvokeRequest {

    /**
     * 请求URL
     */
    String requestUrl;
    /**
     * 请求方式 GET POST ...
     */
    String requestMethod;
    /**
     * 请求参数
     */
    List<RequestField> requestParam;
    /**
     * 请求头
     */
    List<RequestField> requestHeader;

    /**
     * 请求体
     */
    String requestBody;

}
