package com.abing.api.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.abing.api.utils.SignUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author CaptainBing
 * @Date 2023/11/12 21:32
 * @Description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class BingApiClient {

    private String accessKey;

    private String secretKey;

    /**
     * 转换汉字为拼音
     * @param chinese
     * @return
     */
    public String convertChinese2Pinyin(String chinese){

        String url = "http://localhost:9527/sys/simulate/convert";
        Map<String,String> headerMap = new HashMap<>(4);
        headerMap.put("accessKey",accessKey);
        headerMap.put("nonce", RandomUtil.randomNumbers(4));
        headerMap.put("body", "{userName:\"熊炳忠\"}");
        String timestamp = String.valueOf(System.currentTimeMillis());
        headerMap.put("timestamp",timestamp);
        String sign = SignUtils.genSign(timestamp, secretKey);
        headerMap.put("sign", sign);

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("chinese",chinese);
        String body = HttpRequest.of(url)
                .method(Method.GET)
                .addHeaders(headerMap)
                .formStr(paramMap)
                .execute()
                .body();
        log.info("body ===> {}",body);
        return body;
    }

}
