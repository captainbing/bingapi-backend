package com.abing.model.vo;

import com.abing.model.domain.InvokeRecord;
import com.abing.model.request.RequestField;
import lombok.Data;

import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/11/9 17:34
 * @Description
 */
@Data
public class InvokeRecordVO extends InvokeRecord {


    private List<RequestField> requestParams;

    private List<RequestField> requestHeaders;

}
