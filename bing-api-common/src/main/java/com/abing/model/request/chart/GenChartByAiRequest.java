package com.abing.model.request.chart;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 16:18
 * @Description
 */
@Data
public class GenChartByAiRequest {

    /**
     * 图表名称
     */
    private String name;
    /**
     * 分析目标
     */
    private String goal;
    /**
     * 图表类型
     */
    private Integer chartType;

}
