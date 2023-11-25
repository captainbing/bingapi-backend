package com.abing.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.abing.api.client.BingApiClient;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.SysConfig;
import com.abing.model.domain.User;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.model.enums.NativeInterfaceEnum;
import com.abing.model.request.InvokeRequest;
import com.abing.model.request.RequestField;
import com.abing.model.vo.InvokeVO;
import com.abing.service.InterfaceInfoService;
import com.abing.service.InvokeService;
import com.abing.service.SysConfigService;
import com.abing.service.UserService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.abing.model.enums.AvatarSizeEnum.*;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:48
 * @Description
 */
@Service
@RequiredArgsConstructor
public class InvokeServiceImpl implements InvokeService {


    private final InterfaceInfoService interfaceInfoService;

    private final UserService userService;

    private final SysConfigService configService;

    /**
     * 发送请求
     *
     * @param invokeRequest
     * @return
     */
    @Override
    public InvokeVO invokeInterface(InvokeRequest invokeRequest) {

        String requestUrl = invokeRequest.getRequestUrl();
        if (StringUtils.isEmpty(requestUrl)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // TODO 参数校验
        String method = invokeRequest.getRequestMethod().toUpperCase();
        List<RequestField> requestParamField = invokeRequest.getRequestParam();
        List<RequestField> requestHeaderField = invokeRequest.getRequestHeader();

        Map<String, Object> requestParam = requestParamField.stream().collect(Collectors.toMap(RequestField::getRequestKey, RequestField::getRequestValue));
        Map<String, String> requestHeader = requestHeaderField.stream().collect(Collectors.toMap(RequestField::getRequestKey, RequestField::getRequestValue));
        String requestBody = invokeRequest.getRequestBody();

        InterfaceInfo existNativeInterface = interfaceInfoService.getOne(new QueryWrapper<InterfaceInfo>()
                .lambda()
                .eq(InterfaceInfo::getUrl, requestUrl));
        // 本地接口存在
        if (existNativeInterface != null) {
            return this.invokeNativeInterface(requestUrl, method, requestParam, requestHeader, requestBody);
        }
        return this.invokeAnotherInterface(requestUrl, method, requestParam, requestHeader, requestBody);
    }

    @Override
    public InvokeVO invokeAnotherInterface(String requestUrl, String method, Map<String, Object> requestParam, Map<String, String> requestHeader, String requestBody) {
        // TODO 解决 post传参问题  body请求体
        // form 与 body 会发生互相覆盖
        HttpResponse httpResponse = HttpRequest.of(requestUrl)
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
     * @param requestUrl
     * @param method
     * @param requestParam
     * @param requestHeader
     * @param requestBody
     * @return
     */
    @Override
    public InvokeVO invokeNativeInterface(String requestUrl, String method, Map<String, Object> requestParam, Map<String, String> requestHeader, String requestBody) {

        SysConfig currentConfig = configService.getOne(new QueryWrapper<SysConfig>()
                .lambda()
                .eq(SysConfig::getConfigKey, requestUrl));

        ThrowUtils.throwIf(currentConfig == null,ErrorCode.NOT_FOUND_ERROR);

        String id = TokenUtils.getId();
        User currentUser = userService.getById(id);
        ThrowUtils.throwIf(currentUser == null, ErrorCode.NOT_LOGIN_ERROR);
        String accessKey = currentUser.getAccessKey();
        String secretKey = currentUser.getSecretKey();
        BingApiClient bingApiClient = new BingApiClient(accessKey, secretKey);

        NativeInterfaceEnum anEnum = NativeInterfaceEnum.getEnum(currentConfig.getConfigValue());

        InvokeVO invokeVO = new InvokeVO();

        switch (anEnum){
            case HANZI_TO_PINYIN:
                String convertChinese2Pinyin = bingApiClient.convertChinese2Pinyin((String) requestParam.get("chinese"));
                invokeVO.setResponseBody(convertChinese2Pinyin);
                break;
            case FETCH_QQ_AVATAR:
                break;
            default:{}
        }
        return invokeVO;
    }


    /**
     * @param qqRequest
     * @return qq头像url
     */
    @Override
    public String fetchQQAvatar(QQRequest qqRequest) {
        ThrowUtils.throwIf(StringUtils.isEmpty(qqRequest.getQq()), ErrorCode.PARAMS_ERROR);
        String qq = qqRequest.getQq();
        String size = qqRequest.getSize();
        AvatarSizeEnum sizeEnum = AvatarSizeEnum.getEnumByValue(size);
        if (size == null) {
            size = "40";
            return String.format(SearchConstant.QQ_AVATAR_URL, qq, size);
        }
        if (!(PX_40.equals(sizeEnum) || PX_60.equals(sizeEnum) || PX_140.equals(sizeEnum) || PX_640.equals(sizeEnum))) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return String.format(SearchConstant.QQ_AVATAR_URL, qq, size);
    }

}
