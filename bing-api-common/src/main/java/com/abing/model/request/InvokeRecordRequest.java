package com.abing.model.request;

import lombok.Data;

import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/10/30 16:45
 * @Description
 */
@Data
public class InvokeRecordRequest {

    private String title;
    private String parentId;
    private String url;
    private String requestMethod;
    private List<RequestField> requestParam;
    private List<RequestField> requestHeader;
    private String requestBody;
    private List<RequestField> responseHeader;
    private String responseBody;

}
