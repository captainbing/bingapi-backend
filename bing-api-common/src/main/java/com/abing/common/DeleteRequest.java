package com.abing.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author abing
 * @Date 2023/7/21 9:29
 * @Description 删除请求
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}