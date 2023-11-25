package com.abing.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/10/30 16:45
 * @Description
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvokeRecordRequest {

    String id;
    String title;
    String parentId;
    String requestUrl;
    String requestMethod;
    List<RequestField> requestParam;
    List<RequestField> requestHeader;
    String requestBody;
    List<RequestField> responseHeader;
    String responseBody;

}
