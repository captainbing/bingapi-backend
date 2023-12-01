package com.abing.model.vo.interfaceinfo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Author CaptainBing
 * @Date 2023/12/1 21:07
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestParam {

    Long key;

    /**
     * 参数名称
     */
    String name;

    /**
     * 是否必填
     */
    boolean require;

    /**
     * 参数类型
     */
    String type;

    /**
     * 参数描述
     */
    String description;


}
