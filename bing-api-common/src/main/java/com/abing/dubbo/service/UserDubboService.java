package com.abing.dubbo.service;

import com.abing.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author CaptainBing
 * @Date 2023/11/27 21:38
 * @Description
 */
public interface UserDubboService extends IService<User> {

    /**
     * 根据 accessKey 查询用户
     * @param accessKey
     * @return
     */
    User getUserByAccessKey(String accessKey);


}
