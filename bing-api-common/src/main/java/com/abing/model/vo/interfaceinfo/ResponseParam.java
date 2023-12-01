package com.abing.model.vo.interfaceinfo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Author CaptainBing
 * @Date 2023/12/1 21:11
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseParam {

    Long key;

    /**
     * 返回参数键值
     */
    String name;

    /**
     * 返回参数类型
     */
    String type;
    /**
     * 返回参数描述
     */
    String description;

}
