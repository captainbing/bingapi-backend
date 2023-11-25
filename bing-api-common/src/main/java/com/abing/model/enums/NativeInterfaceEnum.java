package com.abing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author CaptainBing
 * @Date 2023/11/25 20:47
 * @Description 本地接口标识
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NativeInterfaceEnum {

    /**
     * 汉字转拼音
     */
    HANZI_TO_PINYIN("HANZI_TO_PINYIN"),

    /**
     * 获取QQ头像
     */
    FETCH_QQ_AVATAR("FETCH_QQ_AVATAR");

    private String text;

    public static NativeInterfaceEnum getEnum(String text){
        if (text == null){
            return null;
        }
        for (NativeInterfaceEnum requestMethodEnum : NativeInterfaceEnum.values()) {
            if (requestMethodEnum.text.equals(text)){
                return requestMethodEnum;
            }
        }
        return null;
    }

}
