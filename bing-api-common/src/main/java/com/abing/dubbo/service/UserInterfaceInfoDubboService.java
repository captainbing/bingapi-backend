package com.abing.dubbo.service;

import com.abing.model.domain.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author CaptainBing
 * @Date 2023/11/29 13:53
 * @Description
 */
public interface UserInterfaceInfoDubboService extends IService<UserInterfaceInfo> {


    /**
     * 调用成功  接口次数 + 1
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    Boolean invokeCountPlusOne(String userId, String interfaceInfoId);


}
