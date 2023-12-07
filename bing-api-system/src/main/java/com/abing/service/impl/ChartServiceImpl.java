package com.abing.service.impl;

import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.abing.manager.AiManager;
import com.abing.mapper.ChartMapper;
import com.abing.model.domain.Chart;
import com.abing.model.enums.ChartTypeEnum;
import com.abing.model.request.chart.GenChartByAiRequest;
import com.abing.model.vo.chart.ChartVO;
import com.abing.service.ChartService;
import com.abing.utils.ExcelUtils;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

/**
* @author 阿炳亿点点帅
* @description 针对表【chart】的数据库操作Service实现
* @createDate 2023-12-05 14:44:46
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    private final AiManager aiManager;

    @Override
    public ChartVO genChartByAi(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile) {

        String name = genChartByAiRequest.getName();
        String goal = genChartByAiRequest.getGoal();
        Integer chartType = genChartByAiRequest.getChartType();
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(name,goal), ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(name.length() > 100, ErrorCode.PARAMS_ERROR);
        ChartTypeEnum anEnum = ChartTypeEnum.getEnum(chartType);
        ThrowUtils.throwIf(anEnum == null,ErrorCode.PARAMS_ERROR,"找不到图表类型");

        StringBuilder userInput = new StringBuilder();
        userInput.append("你是一个数据分析师，接下来我会给你我的分析目标和原始数据，请告诉我分析结论。").append("\n");
        userInput.append("分析需求：")
                .append(goal)
                .append(",请使用")
                .append(anEnum.getValue())
                .append("\n");

        String sourceData = ExcelUtils.excelToCsv(multipartFile);
        userInput.append("原始数据：").append(sourceData).append("\n");
        log.info("用户:{} 用户输入: {} 发送时间: {}", TokenUtils.getUserName(), userInput, LocalDateTime.now());
        String answerResponse = aiManager.doChat(userInput.toString());
        ThrowUtils.throwIf(answerResponse == null,ErrorCode.OPERATION_ERROR,"AI分析出错");
        final String answerSeparator = "【【【【【";
        String[] answerArray = answerResponse.split(answerSeparator);
        final int answerLength = 3;
        if (answerArray.length < answerLength){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"数据返回生成异常,请重试");
        }
        String genChart = answerArray[1];
        String genResult = answerArray[2];

        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setChartData(sourceData);
        chart.setChartType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(TokenUtils.getId());
        chart.setCreateTime(new Date());
        chart.setUpdateTime(new Date());
        boolean saveStatus = this.save(chart);
        ThrowUtils.throwIf(!saveStatus,ErrorCode.OPERATION_ERROR,"插入图表失败");

        ChartVO chartVO = new ChartVO();
        chartVO.setGenChart(genChart);
        chartVO.setGenResult(genResult);
        return chartVO;
    }
}




