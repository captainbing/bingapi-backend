package com.abing.service;

import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRequest;

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
    InvokeRequest invokeAnotherInterface(InvokeRequest invokeRequest);

    /**
     * 获取QQ头像地址
     * @param qqRequest
     * @return
     */
    String fetchQQAvatar(QQRequest qqRequest);
}
