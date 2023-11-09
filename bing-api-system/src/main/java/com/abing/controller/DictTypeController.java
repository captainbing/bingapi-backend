package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.DictType;
import com.abing.service.DictTypeService;
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
 * @Date 2023/9/20 22:20
 * @Description
 */
@RestController
@RequestMapping("/dict/type")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;


    @GetMapping("/list")
    public BaseResponse<IPage<DictType>> listDictType(DictType dictType,
                                                      @RequestParam(defaultValue = "1") Long current,
                                                      @RequestParam(defaultValue = "10") Long size){
        IPage<DictType> dictTypePage = new Page<>();
        dictTypePage.setCurrent(current);
        dictTypePage.setSize(size);
        IPage<DictType> page = dictTypeService.page(dictTypePage, new QueryWrapper<DictType>()
                .lambda()
                .eq(StringUtils.isNotEmpty(dictType.getDictName()), DictType::getDictName, dictType.getDictName())
                .like(StringUtils.isNotEmpty(dictType.getDictType()), DictType::getDictType, dictType.getDictType()));
        return ResultUtils.success(page);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addDictType(@RequestBody DictType dictType){
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(
                dictType.getDictType(),dictType.getDictName()),
                ErrorCode.PARAMS_ERROR);
        dictType.setCreateBy(TokenUtils.getUserName());
        dictType.setCreateTime(new Date());
        dictType.setUpdateBy(TokenUtils.getUserName());
        dictType.setUpdateTime(new Date());
        return ResultUtils.success(dictTypeService.save(dictType));
    }


    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteDictTypeById(DictType dictType){
        ThrowUtils.throwIf(dictType.getId() == null,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(dictTypeService.removeById(dictType.getId()));
    }


    @PostMapping("/edit")
    public BaseResponse<Boolean> updateDictType(@RequestBody DictType dictType){
        ThrowUtils.throwIf(dictType.getId() == null,ErrorCode.PARAMS_ERROR);
        dictType.setUpdateBy(TokenUtils.getUserName());
        dictType.setUpdateTime(new Date());
        return ResultUtils.success(dictTypeService.updateById(dictType));
    }


}
