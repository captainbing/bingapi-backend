package com.abing.service.impl;

import cn.hutool.http.*;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.model.enums.RequestMethodEnum;
import com.abing.model.request.InvokeRequest;
import com.abing.model.request.RequestField;
import com.abing.model.vo.InvokeVO;
import com.abing.service.InvokeRecordService;
import com.abing.service.InvokeService;
import com.abing.service.UserService;
import com.abing.utils.ThrowUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.abing.model.enums.AvatarSizeEnum.*;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:48
 * @Description
 */
@Service
public class InvokeServiceImpl implements InvokeService {


    /**
     * 发送请求
     * @param invokeRequest
     * @return
     */
    @Override
    public InvokeVO invokeAnotherInterface(InvokeRequest invokeRequest) {

        if (StringUtils.isEmpty(invokeRequest.getRequestUrl())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // TODO 参数校验
        String method = invokeRequest.getRequestMethod().toUpperCase();
        List<RequestField> requestParamField = invokeRequest.getRequestParam();
        List<RequestField> requestHeaderField = invokeRequest.getRequestHeader();

        Map<String, Object> requestParam = requestParamField.stream().collect(Collectors.toMap(RequestField::getRequestKey, RequestField::getRequestValue) );
        Map<String, String> requestHeader = requestHeaderField.stream().collect(Collectors.toMap(RequestField::getRequestKey, RequestField::getRequestValue));
        String requestBody = invokeRequest.getRequestBody();

        // form 与 body 会发生互相覆盖
        HttpResponse httpResponse = HttpRequest.of(invokeRequest.getRequestUrl())
                .setMethod(Method.valueOf(method))
                .form(requestParam)
                .addHeaders(requestHeader)
                .timeout(10000)
                .execute();

        Map<String, List<String>> responseHeader = httpResponse.headers();
        // 移除 HTTP/1.1 200  (key null)
        Map<String, List<String>> responseHeaderMap = new HashMap<>();
        responseHeader.forEach((key, value) -> {
            if (key != null) {
                responseHeaderMap.put(key, value);
            }
        });
        String responseBody = httpResponse.body();
        InvokeVO invokeVO = new InvokeVO();
        invokeVO.setResponseHeader(responseHeaderMap);
        invokeVO.setResponseBody(responseBody);
        return invokeVO;
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
