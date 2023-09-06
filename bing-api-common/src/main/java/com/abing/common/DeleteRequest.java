package com.abing.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
    /**
     * ids
     */
    private List<Long> ids;


    private static final long serialVersionUID = 1L;
}