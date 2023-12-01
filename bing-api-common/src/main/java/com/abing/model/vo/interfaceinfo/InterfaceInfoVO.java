package com.abing.model.vo.interfaceinfo;

import lombok.Data;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/8/16 15:07
 * @Description
 */
@Data
public class InterfaceInfoVO {
    private Long id;
    private String name;
    private String desc;
    private List<ProList> content;
}