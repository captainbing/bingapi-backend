package com.abing.model.dto.interfaceinfo;

import com.abing.model.domain.InterfaceInfo;
import lombok.Data;
/**
 * @Author CaptainBing
 * @Date 2023/8/15 15:36
 * @Description
 */
@Data
public class InterfaceInfoDTO extends InterfaceInfo{
    /**
     * 当前用户接口剩余调用次数
     */
    private Long leftNum;
    /**
     * 用户ID
     */
    private Long userId;
}
