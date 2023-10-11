package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.exception.BusinessException;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.service.InvokeRecordService;
import com.abing.service.InvokeService;
import com.abing.utils.ThrowUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 15:32
 * @Description  在线调试调用接口
 */
@RestController
@RequestMapping("/invoke")
public class InvokeRecordController {
    @Resource
    private InvokeService invokeService;

    @Resource
    private InvokeRecordService invokeRecordService;

    @PostMapping("/another")
    public BaseResponse<InvokeRequest> invokeAnotherInterface(@RequestBody InvokeRequest invokeRequest){
        if (StringUtils.isEmpty(invokeRequest.getUrl())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // TODO 参数校验
        return ResultUtils.success(invokeService.invokeAnotherInterface(invokeRequest));
    }


    @GetMapping ("/menu/get")
    public BaseResponse<List<InvokeMenuVO>> getMenuTree(HttpServletRequest request){
        // TODO 参数校验
        return ResultUtils.success(invokeRecordService.getInvokeMenuTree(request));

    }

    @GetMapping ("/menu/add")
    public BaseResponse<Boolean> addMenu(HttpServletRequest request,String title,String parentId){
        ThrowUtils.throwIf(StringUtils.isEmpty(title),ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(invokeRecordService.addMenu(request,title,parentId));

    }

    @GetMapping ("/menu/delete")
    public BaseResponse<Boolean> deleteMenu(HttpServletRequest request,String id){
        ThrowUtils.throwIf(id == null,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(invokeRecordService.deleteMenu(request,id));

    }

    @GetMapping ("/menu/select")
    public BaseResponse selectMenu(String id){
        ThrowUtils.throwIf(id == null,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(invokeRecordService.selectMenu(id));

    }

    @GetMapping("/qq")
    public BaseResponse<String> getQQImage(QQRequest qqRequest){
        if (StringUtils.isEmpty(qqRequest.getQq())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(invokeService.fetchQQAvatar(qqRequest));
    }

    @PostMapping("/post")
    public BaseResponse<String> testPost(@RequestBody TestPost testPost){
        if (StringUtils.isAnyEmpty(testPost.getKey1(),testPost.getKey2())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success("test invoke post " + testPost.getKey1() + testPost.getKey2());
    }

}
@Data
class TestPost{
    String key1;
    String key2;
}
