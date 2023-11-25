package com.abing.service;

import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeVO;

import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:48
 * @Description
 */
public interface InvokeService {

    /**
     * 调用其他接口
     * @param invokeRequest
     * @return
     */
    InvokeVO invokeInterface(InvokeRequest invokeRequest);

    /**
     * 获取QQ头像地址
     * @param qqRequest
     * @return
     */
    String fetchQQAvatar(QQRequest qqRequest);


    /**
     * 调用通用接口
     * @param requestUrl
     * @param method
     * @param requestParam
     * @param requestHeader
     * @param requestBody
     * @return
     */
    InvokeVO invokeAnotherInterface(String requestUrl, String method, Map<String, Object> requestParam, Map<String, String> requestHeader, String requestBody);


    /**
     * 调用本地实现的功能接口
     * @param requestUrl
     * @param method
     * @param requestParam
     * @param requestHeader
     * @param requestBody
     * @return
     */
    InvokeVO invokeNativeInterface(String requestUrl, String method, Map<String, Object> requestParam, Map<String, String> requestHeader, String requestBody);
}
