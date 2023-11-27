package com.abing.service.dubbo;

import com.abing.common.ErrorCode;
import com.abing.dubbo.service.UserDubboService;
import com.abing.exception.BusinessException;
import com.abing.mapper.UserMapper;
import com.abing.model.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author CaptainBing
 * @Date 2023/11/27 21:39
 * @Description
 */
@Service
@org.apache.dubbo.config.annotation.Service
public class UserDubboServiceImpl extends ServiceImpl<UserMapper, User> implements UserDubboService {


    /**
     * @param accessKey
     * @return
     */
    @Override
    public User getUserByAccessKey(String accessKey) {
        User user = this.getOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getAccessKey, accessKey));
        return user;
    }
}
