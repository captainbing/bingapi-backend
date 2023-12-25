package com.abing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author CaptainBing
 * @Date 2023/12/25 17:18
 * @Description
 */
@AllArgsConstructor
@Getter
public enum DefaultTypeEnum {


    NON_DEFAULT(0,"否"),
    DEFAULT(1,"是"),

    ALl(2,"所有");

    private int text;

    private String value;


    /**
     * 根据 value 获取枚举
     *
     * @param text
     * @return
     */
    public static DefaultTypeEnum getEnumByText(int text) {
        if (ObjectUtils.isEmpty(text)) {
            return null;
        }
        for (DefaultTypeEnum anEnum : DefaultTypeEnum.values()) {
            if (anEnum.text == text) {
                return anEnum;
            }
        }
        return null;
    }

}
