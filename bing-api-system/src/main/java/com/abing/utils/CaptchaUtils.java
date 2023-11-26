package com.abing.utils;

import cn.hutool.core.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author CaptainBing
 * @Date 2023/8/12 16:59
 * @Description
 */
public class CaptchaUtils {

    private CaptchaUtils(){}

    /**
     * @return 生成六位随机校验码
     */
    public static String random6Captcha(){
        return RandomUtil.randomString(6);
    }

    /**
     * 转换验证码为Map形式
     * @param captcha
     * @return
     */
    public static Map<String,Object> convertCaptchaToMap(String captcha){

        Map<String,Object> map = new HashMap<>();
        List<String> verifyCodes = Arrays.stream(captcha.split("")).collect(Collectors.toList());
        map.put("verifyCodes",verifyCodes);
        return map;
    }

}
