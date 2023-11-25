package com.abing.model.enums;

import lombok.Getter;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:53
 * @Description
 */
@Getter
public enum RequestMethodEnum {

    Get("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH");
    RequestMethodEnum(String method) {
        this.method = method;
    }
    private String method;

    public static RequestMethodEnum getEnum(String method){
        if (method == null){
            return null;
        }
        for (RequestMethodEnum requestMethodEnum : RequestMethodEnum.values()) {
            if (requestMethodEnum.method.equals(method)){
                return requestMethodEnum;
            }
        }
        return null;
    }
}
