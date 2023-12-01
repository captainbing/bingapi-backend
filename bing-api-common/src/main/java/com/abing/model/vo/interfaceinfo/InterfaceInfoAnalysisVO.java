package com.abing.model.vo.interfaceinfo;

import com.abing.model.domain.InterfaceInfo;
import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/12/1 23:04
 * @Description
 */
@Data
public class InterfaceInfoAnalysisVO extends InterfaceInfo {

    /**
     * 接口总调用次数
     */
    private Long totalNum;

}
