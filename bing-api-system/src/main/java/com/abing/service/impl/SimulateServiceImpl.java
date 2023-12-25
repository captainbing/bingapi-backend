package com.abing.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.manager.AiManager;
import com.abing.model.domain.Chart;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.model.enums.ChartStatusEnum;
import com.abing.model.enums.ChartTypeEnum;
import com.abing.model.request.chart.GenChartByAiRequest;
import com.abing.model.vo.chart.ChartVO;
import com.abing.service.ChartService;
import com.abing.service.SimulateService;
import com.abing.utils.ExcelUtils;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static com.abing.model.enums.AvatarSizeEnum.*;
import static com.abing.model.enums.AvatarSizeEnum.PX_640;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 17:21
 * @Description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SimulateServiceImpl implements SimulateService {


    private final ChartService chartService;

    private final AiManager aiManager;

    private final ThreadPoolExecutor biThreadPoolExecutor;

    @Override
    public String convertChinese2Pinyin(String chinese) {
        ThrowUtils.throwIf(chinese == null, ErrorCode.PARAMS_ERROR);
        return PinyinUtil.getPinyin(chinese);
    }

    @Override
    public String fetchQQAvatar(QQRequest qqRequest) {
        ThrowUtils.throwIf(StringUtils.isEmpty(qqRequest.getQq()),ErrorCode.PARAMS_ERROR);
        String qq = qqRequest.getQq();
        String size = qqRequest.getSize();
        AvatarSizeEnum sizeEnum = AvatarSizeEnum.getEnumByValue(size);
        if (size == null){
            size = PX_40.getSize();
            return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
        }
        if (!(PX_40.equals(sizeEnum) || PX_60.equals(sizeEnum) || PX_140.equals(sizeEnum) || PX_640.equals(sizeEnum))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"图片尺寸不符合要求");
        }
        return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
    }

    @Override
    public ChartVO genChartAsyncByAi(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile) {
        // TODO 优化点
        // guava retrying 重试机制
        // 提前考虑AI 生成错误的情况，提前做出处理
        // 任务未提交到队列中（或者队列满了），使用定时任务将失败状态的图表放在队列中（补偿）
        // 给任务的执行添加一个超时时间，超时自动标记为失败
        // 任务执行成功或失败，给用户发送实时消息通知（websocket，server side event） p0


        // 校验文件
        chartService.checkExcelFileIsLegal(multipartFile);

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

        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setChartData(sourceData);
        chart.setChartType(chartType);
        chart.setStatus(ChartStatusEnum.WAITING.getText());
        chart.setExecMessage(ChartStatusEnum.WAITING.getValue());
        chart.setUserId(TokenUtils.getId());
        chart.setCreateTime(new Date());
        chart.setUpdateTime(new Date());
        boolean saveStatus = chartService.save(chart);
        ThrowUtils.throwIf(!saveStatus,ErrorCode.OPERATION_ERROR,"插入图表失败");

        // 后台线程处理任务
        CompletableFuture.runAsync(()->{

            Chart updateChart = new Chart();
            updateChart.setId(chart.getId());
            updateChart.setStatus(ChartStatusEnum.EXECUTING.getText());
            boolean b = chartService.updateById(updateChart);
            if (!b){
                handleChartError(chart.getId(),"更新图表执行中状态失败");
            }

            String answerResponse = aiManager.doChat(userInput.toString());
            ThrowUtils.throwIf(answerResponse == null,ErrorCode.OPERATION_ERROR,"AI分析出错");
            final String answerSeparator = "【【【【【";
            String[] answerArray = answerResponse.split(answerSeparator);
            final int answerLength = 3;
            if (answerArray.length < answerLength){
                throw new BusinessException(ErrorCode.OPERATION_ERROR,"数据返回生成异常,请重试");
            }
            String genChart = answerArray[1].trim();
            String genResult = answerArray[2].trim();

            Chart updateChartResult = new Chart();
            updateChartResult.setId(chart.getId());
            updateChartResult.setStatus(ChartStatusEnum.SUCCEED.getText());
            updateChartResult.setGenChart(genChart);
            updateChartResult.setGenResult(genResult);
            boolean updateStatus = chartService.updateById(updateChartResult);
            if (!updateStatus){
                handleChartError(chart.getId(),"更新图表成功状态失败");
            }
        },biThreadPoolExecutor);

        ChartVO chartVO = new ChartVO();
        chartVO.setChartId(chart.getId());
        return chartVO;
    }

    /**
     * 处理图表生成错误
     * @param chartId
     * @param execMessage
     */
    private void handleChartError(long chartId,String execMessage){

        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus(ChartStatusEnum.FAILED.getText());
        updateChartResult.setExecMessage(execMessage);
        boolean updateStatus = chartService.updateById(updateChartResult);
        if (!updateStatus){
            log.info("图表生成错误，图表ID:{} 错误信息:{}",chartId,execMessage);
        }
    }

}
