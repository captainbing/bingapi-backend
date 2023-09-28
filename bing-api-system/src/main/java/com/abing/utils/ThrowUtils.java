package com.abing.utils;

import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;

/**
 * @Author CaptainBing
 * @Date 2023/9/26 20:22
 * @Description 异常工具类
 */
public class ThrowUtils {

    private ThrowUtils(){}

    /**
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode){
        if (condition){
            throw new BusinessException(errorCode);
        }
    }

    /**
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode,String message){
        if (condition){
            throw new BusinessException(errorCode,message);
        }
    }


}
