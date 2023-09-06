package com.abing.model.dto.interfaceinfo;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/8/21 19:22
 * @Description
 */
@Data
public class SearchInterfaceRequest {
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口状态
     */
    private String status;
    /**
     * 接口方法
     */
    private String method;

    private Long current;

    private Long size;


}
