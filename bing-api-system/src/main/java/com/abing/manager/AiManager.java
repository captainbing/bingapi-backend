package com.abing.manager;

import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 17:51
 * @Description
 */
@Component
@RequiredArgsConstructor
public class AiManager {

    private final YuCongMingClient yuCongMingClient;

    /**
     * 与AI对话 获取返回数据
     * @param message
     * @return
     */
    public String doChat(String message){
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1731973374676520962L);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> baseResponse = yuCongMingClient.doChat(devChatRequest);
        if (baseResponse == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"AI 响应错误");
        }
        return baseResponse.getData().getContent();
    }

}
