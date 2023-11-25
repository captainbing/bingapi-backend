package com.abing.model.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 11:16
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvokeVO {
    /**
     * 响应头
     */
    Map<String, List<String>> responseHeader;

    /**
     * 响应体
     */
    String responseBody;

}
