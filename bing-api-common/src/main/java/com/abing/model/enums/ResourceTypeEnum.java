package com.abing.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author CaptainBing
 * @Date 2023/10/21 15:23
 * @Description
 */
@Getter
public enum ResourceTypeEnum {

    /**
     * 目录
     */
    DIRECTORY("0","目录"),

    /**
     * 文件
     */
    FILE("1","文件");

    private String code;
    private String value;

    ResourceTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static ResourceTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ResourceTypeEnum anEnum : ResourceTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
