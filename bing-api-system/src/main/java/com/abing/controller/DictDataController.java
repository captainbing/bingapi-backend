package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.DictData;
import com.abing.model.domain.DictType;
import com.abing.service.DictDataService;
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
 * @Date 2023/9/20 22:19
 * @Description
 */
@RestController
@RequestMapping("/dict/data")
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @GetMapping("/list")
    public BaseResponse<IPage<DictData>> listDictData(DictType dictType,
                                                      @RequestParam(defaultValue = "1") Long current,
                                                      @RequestParam(defaultValue = "10") Long size) {
        ThrowUtils.throwIf(StringUtils.isEmpty(dictType.getDictType()), ErrorCode.PARAMS_ERROR);
        IPage<DictData> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        IPage<DictData> dictDataPage = dictDataService.page(page, new QueryWrapper<DictData>()
                .lambda()
                .eq(DictData::getDictType, dictType.getDictType()));
        return ResultUtils.success(dictDataPage);
    }

    @GetMapping("/get")
    public BaseResponse<DictData> getDictDataById(DictData dictData) {
        ThrowUtils.throwIf(dictData.getId() == null, ErrorCode.PARAMS_ERROR);
        DictData currentDictData = dictDataService.getById(dictData.getId());
        ThrowUtils.throwIf(currentDictData == null, ErrorCode.OPERATION_ERROR, "当前配置不存在");
        return ResultUtils.success(currentDictData);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addDictData(@RequestBody DictData dictData) {
        ThrowUtils.throwIf(
                StringUtils.isAnyEmpty(dictData.getDictType(), dictData.getDictLabel(), dictData.getDictLabel()),
                ErrorCode.PARAMS_ERROR);
        dictData.setCreateBy(TokenUtils.getUserName());
        dictData.setCreateTime(new Date());
        dictData.setUpdateBy(TokenUtils.getUserName());
        dictData.setUpdateTime(new Date());
        return ResultUtils.success(dictDataService.save(dictData));
    }


    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteDictDataById(DictData dictData) {
        ThrowUtils.throwIf(dictData.getId() == null, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(dictDataService.removeById(dictData.getId()));
    }


    @PostMapping("/edit")
    public BaseResponse<Boolean> updateDictData(@RequestBody DictData dictData) {
        ThrowUtils.throwIf(dictData.getId() == null, ErrorCode.PARAMS_ERROR);
//        dictData.setUpdateBy(TokenUtils.getLoginUserName());
        dictData.setUpdateTime(new Date());
        return ResultUtils.success(dictDataService.updateById(dictData));
    }

}
