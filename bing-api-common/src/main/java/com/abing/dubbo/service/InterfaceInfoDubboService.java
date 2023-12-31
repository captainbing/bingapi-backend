package com.abing.dubbo.service;

import com.abing.model.domain.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 阿炳亿点点帅
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-07-22 17:15:22
*/
public interface InterfaceInfoDubboService extends IService<InterfaceInfo> {


    /**
     * 根据请求路径获取接口信息
     * @param url
     * @return
     */
    InterfaceInfo getInterfaceInfoByUrl(String url);


}
