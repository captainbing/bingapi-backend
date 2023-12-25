package com.abing.service;

import com.abing.model.domain.Chart;
import com.abing.model.request.chart.GenChartByAiRequest;
import com.abing.model.vo.chart.ChartVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 阿炳亿点点帅
* @description 针对表【chart】的数据库操作Service
* @createDate 2023-12-05 14:44:46
*/
public interface ChartService extends IService<Chart> {

    /**
     * 根据AI生成分析结果
     * @param genChartByAiRequest
     * @param multipartFile
     * @return
     */
    ChartVO genChartByAi(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile);

    /**
     * 校验文件是否合法
     * @param multipartFile
     */
    void checkExcelFileIsLegal(MultipartFile multipartFile);

}
