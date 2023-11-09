package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.SysConfig;
import com.abing.service.SysConfigService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author CaptainBing
 * @Date 2023/9/23 14:35
 * @Description
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private SysConfigService configService;


    @GetMapping("/list")
    public BaseResponse<IPage<SysConfig>> listConfig(SysConfig config,
                                                      @RequestParam(defaultValue = "1") Long current,
                                                      @RequestParam(defaultValue = "10") Long size){
        IPage<SysConfig> configPage = new Page<>();
        configPage.setCurrent(current);
        configPage.setSize(size);
        IPage<SysConfig> page = configService.page(configPage, new QueryWrapper<SysConfig>()
                .lambda()
                .eq(StringUtils.isNotEmpty(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
                .eq(StringUtils.isNotEmpty(config.getConfigType()), SysConfig::getConfigType, config.getConfigType()));
        return ResultUtils.success(page);
    }

    @GetMapping("/get")
    public BaseResponse<SysConfig> getConfigById(SysConfig config){
        ThrowUtils.throwIf(config.getId() == null,ErrorCode.PARAMS_ERROR);
        SysConfig currentConfig = configService.getById(config.getId());
        ThrowUtils.throwIf(currentConfig == null,ErrorCode.OPERATION_ERROR,"当前配置不存在");
        return ResultUtils.success(currentConfig);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addConfig(SysConfig config){
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(
                config.getConfigKey(),config.getConfigValue()),
                ErrorCode.PARAMS_ERROR);
        config.setCreateBy(TokenUtils.getUserName());
        config.setCreateTime(new Date());
        config.setUpdateBy(TokenUtils.getUserName());
        config.setUpdateTime(new Date());
        return ResultUtils.success(configService.save(config));
    }

    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteConfigById(SysConfig config){
        ThrowUtils.throwIf(config.getId() == null,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(configService.removeById(config.getId()));
    }


    @PostMapping("/edit")
    public BaseResponse<Boolean> updateConfig(@RequestBody SysConfig config){
        ThrowUtils.throwIf(config.getId() == null,ErrorCode.PARAMS_ERROR);
        config.setUpdateBy(TokenUtils.getUserName());
        config.setUpdateTime(new Date());
        return ResultUtils.success(configService.updateById(config));
    }

}
