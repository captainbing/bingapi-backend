package com.abing.model.dto.search;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/8/20 13:34
 * @Description
 */
@Data
public class PictureQueryRequest {
    private String searchText;
    private Long current;
    private Long pageSize;
}
