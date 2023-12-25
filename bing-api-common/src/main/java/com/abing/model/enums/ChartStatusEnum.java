package com.abing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author CaptainBing
 * @Date 2023/12/25 15:50
 * @Description 图表生成状态枚举
 */
@AllArgsConstructor
@Getter
public enum ChartStatusEnum {

    WAITING(0,"等待中"),
    EXECUTING(1,"执行中"),

    SUCCEED(2,"生成成功"),

    FAILED(3,"生成失败");


    private int text;

    private String value;


    /**
     * 根据 value 获取枚举
     *
     * @param text
     * @return
     */
    public static ChartStatusEnum getEnumByText(int text) {
        if (ObjectUtils.isEmpty(text)) {
            return null;
        }
        for (ChartStatusEnum anEnum : ChartStatusEnum.values()) {
            if (anEnum.text == text) {
                return anEnum;
            }
        }
        return null;
    }


}
