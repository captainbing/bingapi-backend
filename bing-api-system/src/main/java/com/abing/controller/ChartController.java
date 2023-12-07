package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.Chart;
import com.abing.model.request.chart.GenChartByAiRequest;
import com.abing.model.vo.chart.ChartVO;
import com.abing.service.ChartService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 14:48
 * @Description
 */
@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @PostMapping("/gen")
    public BaseResponse<ChartVO> genChartByAi(@RequestPart("file") MultipartFile multipartFile,
                                              GenChartByAiRequest genChartByAiRequest) {
        return ResultUtils.success(chartService.genChartByAi(genChartByAiRequest, multipartFile));
    }


    @GetMapping("/list")
    public BaseResponse<IPage<Chart>> listChartByPage(@RequestParam(defaultValue = "1",required = false) Long current,
                                                      @RequestParam(defaultValue = "10",required = false) Long size,
                                                      @RequestParam(value = "name",required = false) String name) {
        String userId = TokenUtils.getId();
        IPage<Chart> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        IPage<Chart> chartPage = chartService.page(page, new QueryWrapper<Chart>()
                .lambda()
                .eq(Chart::getUserId, userId)
                .like(StringUtils.isNotEmpty(name),Chart::getName,name)
                .orderByDesc(Chart::getCreateTime));
        return ResultUtils.success(chartPage);
    }

    @GetMapping("/get")
    public BaseResponse<Chart> getChartById(Chart chart) {
        ThrowUtils.throwIf(chart.getId() == null, ErrorCode.PARAMS_ERROR);
        Chart currentChart = chartService.getById(chart.getId());
        ThrowUtils.throwIf(currentChart == null, ErrorCode.OPERATION_ERROR, "当前配置不存在");
        return ResultUtils.success(currentChart);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addChart(@RequestBody Chart chart, @RequestPart("file") MultipartFile file) {
        String userId = TokenUtils.getId();
        ThrowUtils.throwIf(
                StringUtils.isAnyEmpty(chart.getGoal()) || file.isEmpty(),
                ErrorCode.PARAMS_ERROR);
        chart.setUserId(userId);
        chart.setCreateTime(new Date());
        chart.setUpdateTime(new Date());
        return ResultUtils.success(chartService.save(chart));
    }


    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteChartById(Chart chart) {
        ThrowUtils.throwIf(chart.getId() == null, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(chartService.removeById(chart.getId()));
    }


    @PutMapping("/edit")
    public BaseResponse<Boolean> updateChart(@RequestBody Chart chart) {
        ThrowUtils.throwIf(chart.getId() == null, ErrorCode.PARAMS_ERROR);
        chart.setUpdateTime(new Date());
        return ResultUtils.success(chartService.updateById(chart));
    }


}
