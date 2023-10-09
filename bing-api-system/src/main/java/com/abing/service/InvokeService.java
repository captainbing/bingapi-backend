package com.abing.service;

import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;

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
    InvokeRequest invokeAnotherInterface(InvokeRequest invokeRequest);

    /**
     * 获取QQ头像地址
     * @param qqRequest
     * @return
     */
    String fetchQQAvatar(QQRequest qqRequest);

    /**
     * 获取树形目录结构
     * @return
     */
    List<InvokeMenuVO> getInvokeMenuTree(HttpServletRequest request);

    /**
     * 添加菜单目录
     * @param request
     * @param title
     * @return
     */
    Boolean addMenu(HttpServletRequest request,String title);
}
