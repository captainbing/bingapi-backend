package com.abing.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.model.enums.RequestMethodEnum;
import com.abing.model.request.InvokeRequest;
import com.abing.service.InvokeRecordService;
import com.abing.service.InvokeService;
import com.abing.service.UserService;
import com.abing.utils.ThrowUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.abing.model.enums.AvatarSizeEnum.*;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:48
 * @Description
 */
@Service
public class InvokeServiceImpl implements InvokeService {

    @Resource
    private InvokeRecordService invokeRecordService;
    
    @Resource
    private UserService userService;

    /**
     * @param invokeRequest TODO GET POST
     * @return
     */
    @Override
    public InvokeRequest invokeAnotherInterface(InvokeRequest invokeRequest) {
        if (StringUtils.isEmpty(invokeRequest.getUrl())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // TODO 参数校验
        String method = invokeRequest.getMethod().toUpperCase();
        RequestMethodEnum requestMethodEnum = RequestMethodEnum.getEnum(method);
        switch (requestMethodEnum){
            case Get:

                String getBody = HttpRequest.of(invokeRequest.getUrl())
                        .setMethod(Method.GET)
                        .execute()
                        .body();
                System.out.println(getBody);
                InvokeRequest getRequest = new InvokeRequest();
                getRequest.setBaseResponse(getBody);
                return getRequest;
            case POST:
                Map<String, String> requestParams = invokeRequest.getRequestParams();
                String body = "";
                if (requestParams != null){
                    Gson gson = new Gson();
                    body = gson.toJson(requestParams);
                }
                String postBody = HttpRequest.of(invokeRequest.getUrl())
                        .setMethod(Method.POST)
                        .body(body)
                        .execute()
                        .body();
                System.out.println(postBody);
                InvokeRequest postRequest = new InvokeRequest();
                postRequest.setBaseResponse(postBody);
                return postRequest;
            case PUT:
                break;
            case DELETE:
                break;
            case OPTIONS:
                break;
            case PATCH:
                break;
            default:{
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return null;
    }

    /**
     * @param qqRequest
     * @return qq头像url
     */
    @Override
    public String fetchQQAvatar(QQRequest qqRequest) {
        ThrowUtils.throwIf(StringUtils.isEmpty(qqRequest.getQq()),ErrorCode.PARAMS_ERROR);
        String qq = qqRequest.getQq();
        String size = qqRequest.getSize();
        AvatarSizeEnum sizeEnum = AvatarSizeEnum.getEnumByValue(size);
        if (size == null){
            size = "40";
            return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
        }
        if (!(PX_40.equals(sizeEnum) || PX_60.equals(sizeEnum) || PX_140.equals(sizeEnum) || PX_640.equals(sizeEnum))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
    }
}
