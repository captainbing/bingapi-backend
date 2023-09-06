package com.abing.model.enums;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author CaptainBing
 * @Date 2023/8/21 16:51
 * @Description
 */
public enum AvatarSizeEnum {

    PX_40("40"),
    PX_60("100"),
    PX_140("140"),
    PX_640("640");
    private String size;

    AvatarSizeEnum(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static AvatarSizeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (AvatarSizeEnum anEnum : AvatarSizeEnum.values()) {
            if (anEnum.size.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
