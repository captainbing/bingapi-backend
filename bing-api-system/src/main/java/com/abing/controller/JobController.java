package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.SysJob;
import com.abing.service.SysJobService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author CaptainBing
 * @Date 2023/9/24 19:35
 * @Description
 */
@RestController
@RequestMapping("/job")
public class JobController {
    
    
    @Resource
    private SysJobService jobService;

    @GetMapping("/list")
    public BaseResponse<IPage<SysJob>> listJob(SysJob job,
                                                     @RequestParam(defaultValue = "1") Long current,
                                                     @RequestParam(defaultValue = "10") Long size){
        IPage<SysJob> jobPage = new Page<>();
        jobPage.setCurrent(current);
        jobPage.setSize(size);
        IPage<SysJob> page = jobService.page(jobPage, new QueryWrapper<SysJob>()
                .lambda()
                .like(StringUtils.isNotEmpty(job.getJobName()), SysJob::getJobName, job.getJobName())
                .eq(StringUtils.isNotEmpty(job.getJobGroup()), SysJob::getJobGroup, job.getJobGroup()));
        return ResultUtils.success(page);
    }

    @GetMapping("/get")
    public BaseResponse<SysJob> getJobById(SysJob job){
        ThrowUtils.throwIf(job.getId() == null,ErrorCode.PARAMS_ERROR);
        SysJob currentConfig = jobService.getById(job.getId());
        ThrowUtils.throwIf(currentConfig == null,ErrorCode.OPERATION_ERROR,"当前任务不存在");
        return ResultUtils.success(currentConfig);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addJob(SysJob job){
        ThrowUtils.throwIf(CronExpression.isValidExpression(job.getCronExpression()),ErrorCode.PARAMS_ERROR,"cron表达式不合法");
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(job.getJobName(),job.getJobGroup()),ErrorCode.PARAMS_ERROR);
        job.setCreateBy(TokenUtils.getUserName());
        job.setCreateTime(new Date());
        job.setUpdateBy(TokenUtils.getUserName());
        job.setUpdateTime(new Date());
        return ResultUtils.success(jobService.save(job));
    }

    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteJobById(SysJob job){
        ThrowUtils.throwIf(job.getId() == null,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(jobService.removeById(job.getId()));
    }


    @PostMapping("/edit")
    public BaseResponse<Boolean> updateJob(@RequestBody SysJob job){
        ThrowUtils.throwIf(job.getId() == null,ErrorCode.PARAMS_ERROR);
        job.setUpdateBy(TokenUtils.getUserName());
//        job.setUpdateTime(new Date());
        return ResultUtils.success(jobService.updateById(job));
    }


    @PostMapping("/status/change")
    public BaseResponse<Boolean> changeJobStatus(@RequestBody SysJob job){
        ThrowUtils.throwIf(job.getId() == null,ErrorCode.PARAMS_ERROR);
        job.setUpdateBy(TokenUtils.getUserName());
//        job.setUpdateTime(new Date());
        return ResultUtils.success(jobService.updateById(job));
    }
    
}
