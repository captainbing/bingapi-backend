package com.abing.utils;

import java.util.Random;

/**
 * @Author CaptainBing
 * @Date 2023/8/12 16:59
 * @Description
 */
public class CaptchaUtil {

    private CaptchaUtil(){}

    public static Random random = new Random();

    /**
     * @return 生成六位随机校验码
     */
    public static String random6Captcha(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

}
