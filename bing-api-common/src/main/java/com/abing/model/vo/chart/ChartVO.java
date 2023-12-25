package com.abing.model.vo.chart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 18:54
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChartVO {

    /**
     * 图表ID
     */
    Long chartId;

    /**
     * 生成的图表数据
     */
    String genChart;

    /**
     * 分析结论
     */
    String genResult;

}
