package com.abing.model.dto.interfaceinfo;

import com.abing.model.domain.InterfaceInfo;
import lombok.Data;
/**
 * @Author CaptainBing
 * @Date 2023/8/15 15:36
 * @Description
 */
@Data
public class InterfaceInfoDTO{

    /**
     * API ID 接口Id
     */
    private Long id;

    /**
     * API状态
     */
    private Integer status;

    /**
     * 当前用户接口剩余调用次数
     */
    private Long totalNum;

    /**
     * API名称
     */
    private String name;

    /**
     * API描述
     */
    private String description;
}
