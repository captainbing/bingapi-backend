package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.DeleteRequest;
import com.abing.common.ResultUtils;
import com.abing.model.domain.InterfaceInfo;
import com.abing.service.InterfaceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author abing
 * @Date 2023/7/24 18:35
 * @Description 接口信息
 */
@RestController
@RequestMapping("/interface")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/get/{id}")
    public BaseResponse<InterfaceInfo> getInterfaceInfo(@PathVariable Integer id){
        return ResultUtils.success(interfaceInfoService.getById(id));
    }
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(Integer id){
        return ResultUtils.success(interfaceInfoService.getById(id));
    }

    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> getInterfaceInfo(){
        return ResultUtils.success(interfaceInfoService.list());
    }

    @PostMapping("/save")
    public BaseResponse<Boolean> saveInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo){
        return ResultUtils.success(interfaceInfoService.save(interfaceInfo));
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo){
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
    }
    @GetMapping("/delete/")
    public BaseResponse<Boolean> deleteInterfaceInfo(DeleteRequest deleteRequest){
        return ResultUtils.success(interfaceInfoService.removeById(deleteRequest.getId()));
    }
}
