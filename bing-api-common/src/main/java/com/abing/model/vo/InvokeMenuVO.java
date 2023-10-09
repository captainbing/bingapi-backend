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

    private String title;

    private String key;

    private Integer parentId;

    private Boolean isLeaf;

    private List<InvokeMenuVO> children = new ArrayList<>();

}
