package com.abing.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.abing.constant.UserConstant;
import org.springframework.util.DigestUtils;

/**
 * @Author CaptainBing
 * @Date 2023/8/12 17:26
 * @Description
 */
public class EncryptUtils {

    private EncryptUtils(){}
    /**
     * MD5加密密码
     * @param password
     * @return
     */
    public static String genEncryptPasswordMd5(String password){
        return DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes());
    }

    /**
     * 生成密钥
     * @param userAccount
     * @return
     */
    public static String genEncryptKeyMd5(String userAccount){
        return DigestUtil.md5Hex(UserConstant.SALT + userAccount + RandomUtil.randomNumbers(8));
    }

}
