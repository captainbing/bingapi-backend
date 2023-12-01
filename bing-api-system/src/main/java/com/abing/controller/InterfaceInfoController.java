package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.DeleteRequest;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.vo.interfaceinfo.InterfaceInfoAnalysisVO;
import com.abing.model.vo.interfaceinfo.InterfaceInfoDrawer;
import com.abing.service.InterfaceInfoService;
import com.abing.exception.BusinessException;
import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.User;
import com.abing.model.dto.interfaceinfo.InterfaceInfoDTO;
import com.abing.model.dto.interfaceinfo.SearchInterfaceRequest;
import com.abing.model.vo.interfaceinfo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author abing
 * @Date 2023/7/24 18:35
 * @Description 接口信息
 */
@RestController
@RequestMapping("/interface")
@RequiredArgsConstructor
public class InterfaceInfoController {

    private final InterfaceInfoService interfaceInfoService;

    @GetMapping("/get")
    public BaseResponse<InterfaceInfoDrawer> getInterfaceInfoById(Long id){
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoById(id));
    }

    @PostMapping("/save")
    public BaseResponse<Boolean> saveInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo){
        return ResultUtils.success(interfaceInfoService.save(interfaceInfo));
    }

    @PostMapping("/list")
    public BaseResponse<IPage<InterfaceInfo>> searchOrListInterface(@RequestBody SearchInterfaceRequest searchInterfaceRequest){
        return ResultUtils.success(interfaceInfoService.searchInterface(searchInterfaceRequest));
    }

    @PostMapping("/editInterface")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo){
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
    }
    @PostMapping("/remove")
    public BaseResponse<Boolean> removeInterfaceInfoBatch(@RequestBody DeleteRequest deleteRequest){
        if (deleteRequest.getIds() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.removeInterfaceInfoBatch(deleteRequest.getIds()));
    }


    @GetMapping("/listInterfaces")
    public BaseResponse<List<InterfaceInfoVO>> listInterfaces(User user){
        if (user.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.listInterfaces(user));
    }

    @GetMapping("/search")
    public BaseResponse<List<InterfaceInfoVO>> searchInterfacesByName(InterfaceInfoDTO interfaceRequest){
        if (interfaceRequest.getName() == null || interfaceRequest.getUserId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.searchInterfacesByName(interfaceRequest));
    }

    @GetMapping("/analysis")
    public BaseResponse<List<InterfaceInfoAnalysisVO>> listTopInterfaceInfo(){
        return ResultUtils.success(interfaceInfoService.listTopInterfaceInfo());
    }
}
