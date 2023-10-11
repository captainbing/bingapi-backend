package com.abing.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/10/7 21:50
 * @Description
 */
@Data
public class InvokeMenuVO {

    /**
     * 接口目录
     */
    private String title;

    /**
     * 添加菜单的值
     */
    private String value;

    private String key;

    private String parentId;

    private Boolean isLeaf;

    private List<InvokeMenuVO> children = new ArrayList<>();

}
