package com.abing.service;

import com.abing.model.dto.search.QQRequest;
import com.abing.model.request.chart.GenChartByAiRequest;
import com.abing.model.vo.chart.ChartVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 17:21
 * @Description
 */

public interface SimulateService {

    /**
     * 汉字转拼音
     * @param chinese
     * @return
     */
    String convertChinese2Pinyin(String chinese);

    /**
     * 获取QQ头像地址
     * @param qqRequest
     * @return
     */
    String fetchQQAvatar(QQRequest qqRequest);

    /**
     * 异步生成图表
     * @param genChartByAiRequest
     * @param multipartFile
     * @return
     */
    ChartVO genChartAsyncByAi(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile);
}
