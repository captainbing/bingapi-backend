package com.abing.model.dto.search;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/8/21 16:32
 * @Description
 */
@Data
public class QQRequest {

    /**
     * qq账号
     */
    private String qq;
    /**
     * 返回图片大小
     */
    private String size;

}
