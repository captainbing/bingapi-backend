package com.abing.service;

import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRecordRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.model.vo.InvokeVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    InvokeVO invokeAnotherInterface(InvokeRequest invokeRequest);

    /**
     * 获取QQ头像地址
     * @param qqRequest
     * @return
     */
    String fetchQQAvatar(QQRequest qqRequest);

}
