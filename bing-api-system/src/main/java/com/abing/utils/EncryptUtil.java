package com.abing.utils;

import com.abing.constant.UserConstant;
import org.springframework.util.DigestUtils;

/**
 * @Author CaptainBing
 * @Date 2023/8/12 17:26
 * @Description
 */
public class EncryptUtil {

    private EncryptUtil(){}
    /**
     * MD5加密密码
     * @param password
     * @return
     */
    public static String enCryptPasswordMd5(String password){
        return DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes());
    }

}
