package com.abing.utils;

import java.util.UUID;

/**
 * @Author CaptainBing
 * @Date 2023/10/9 22:54
 * @Description
 */
public class UUIDUtils {

    private UUIDUtils(){}

    public static String simpleID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
