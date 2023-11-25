package com.abing.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.abing.constant.UserConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author CaptainBing
 * @Date 2023/11/25 15:48
 * @Description
 */
class UserServiceImplTest {

    @Test
    void userLoginByCaptcha() {

        String accessKey = DigestUtil.md5Hex(UserConstant.SALT + "750321038@qq.com" + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(UserConstant.SALT + "750321038@qq.com" + RandomUtil.randomNumbers(8));
        System.out.println(accessKey);
        System.out.println(secretKey);


    }
}