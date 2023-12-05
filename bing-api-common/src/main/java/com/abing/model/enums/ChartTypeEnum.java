package com.abing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 16:22
 * @Description
 */
@Getter
@AllArgsConstructor
public enum ChartTypeEnum {

    RADAR_CHART(0,"雷达"),
    PIE_CHART(1,"饼图"),
    BAR_CHART(2,"柱状图"),
    LINE_CHART(3,"折线图"),
    SCATTER_PLOT_CHART(4,"散点图"),
    K_DIAGRAM_CHART(5,"K线图");
    private Integer text;

    private String value;

    public static ChartTypeEnum getEnum(Integer text){
        if (text == null){
            return null;
        }
        for (ChartTypeEnum requestMethodEnum : ChartTypeEnum.values()) {
            if (requestMethodEnum.text.equals(text)){
                return requestMethodEnum;
            }
        }
        return null;
    }
}
