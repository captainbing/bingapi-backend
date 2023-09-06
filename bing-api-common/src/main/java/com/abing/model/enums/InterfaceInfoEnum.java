package com.abing.model.enums;


/**
 * @Author CaptainBing
 * @Date 2023/8/16 16:23
 * @Description 接口状态
 */

public enum InterfaceInfoEnum {

    CLOSE(0,"关闭"),
    AVAILABLE(1,"可用");
    InterfaceInfoEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
    private Integer status;
    private String description;

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
