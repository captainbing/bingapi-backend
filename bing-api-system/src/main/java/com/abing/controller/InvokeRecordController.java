package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.model.domain.InvokeRecord;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRecordRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.model.vo.InvokeRecordVO;
import com.abing.model.vo.InvokeVO;
import com.abing.service.InvokeRecordService;
import com.abing.service.InvokeService;
import com.abing.utils.ThrowUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 15:32
 * @Description  在线调试调用接口
 */
@RestController
@RequestMapping("/invoke")
@RequiredArgsConstructor
public class InvokeRecordController {
    private final InvokeService invokeService;

    private final InvokeRecordService invokeRecordService;


    @PostMapping("/another")
    public BaseResponse<InvokeVO> invokeAnotherInterface(@RequestBody InvokeRequest invokeRequest){
        return ResultUtils.success(invokeService.invokeInterface(invokeRequest));
    }

    @PostMapping("/record/add")
    public BaseResponse<Boolean> addInterfaceRecord(@RequestBody InvokeRecordRequest invokeRecordRequest){
        return ResultUtils.success(invokeRecordService.saveInvokeRecord(invokeRecordRequest));
    }

    @GetMapping("/record/get")
    public BaseResponse<InvokeRecordVO> getInvokeRecordById(@RequestParam(value = "id",required = false) String id){
        return ResultUtils.success(invokeRecordService.getInvokeRecordById(id));
    }

    @GetMapping("/record/copy")
    public BaseResponse<Boolean> copyInvokeRecordById(@RequestParam(value = "id",required = false) String id){
        return ResultUtils.success(invokeRecordService.copyInvokeRecordById(id));
    }

    @PostMapping("/record/recover")
    public BaseResponse<Boolean> recoverInvokeRecordById(@RequestBody InvokeRecordRequest invokeRecordRequest){
        return ResultUtils.success(invokeRecordService.recoverInvokeRecord(invokeRecordRequest));
    }

    @PostMapping("/record/update")
    public BaseResponse<Boolean> updateInvokeRecordById(@RequestBody InvokeRecordRequest invokeRecordRequest){
        return ResultUtils.success(invokeRecordService.updateInvokeRecordById(invokeRecordRequest));
    }


    @GetMapping ("/menu/get")
    public BaseResponse<List<InvokeMenuVO>> listMenuTree(){
        // TODO 参数校验
        return ResultUtils.success(invokeRecordService.getInvokeMenuTree());

    }

    @PostMapping ("/menu/add")
    public BaseResponse<Boolean> addMenu(@RequestBody InvokeRecord invokeRecord){
        return ResultUtils.success(invokeRecordService.addMenu(invokeRecord));

    }

    @GetMapping ("/menu/delete")
    public BaseResponse<Boolean> deleteMenu(@RequestParam(value = "id",required = false) String id){
        return ResultUtils.success(invokeRecordService.deleteMenu(id));

    }

    @PostMapping ("/menu/edit")
    public BaseResponse<Boolean> editMenu(@RequestBody InvokeRecord invokeRecord){
        ThrowUtils.throwIf(StringUtils.isEmpty(invokeRecord.getId()),ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(invokeRecordService.editMenu(invokeRecord));
    }

    @GetMapping ("/menu/select")
    public BaseResponse<List<InvokeMenuVO>> selectMenu(String id){
        return ResultUtils.success(invokeRecordService.selectMenu(id));

    }

    @GetMapping("/qq")
    public BaseResponse<String> getQQImage(QQRequest qqRequest){
        return ResultUtils.success(invokeService.fetchQQAvatar(qqRequest));
    }

    @GetMapping("/test")
    public BaseResponse<String> test(@RequestParam(value = "userName",required = false) String userName,
                                     @RequestParam(value = "age",required = false) String age){
        return ResultUtils.success("userName:" + userName + " age:" + age);
    }

}
