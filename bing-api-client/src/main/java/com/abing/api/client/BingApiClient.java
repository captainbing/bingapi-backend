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

    private static String SIMULATE_HOST = "http://localhost:9527";

    /**
     * 转换汉字为拼音
     * @param chinese
     * @return
     */
    public String convertChinese2Pinyin(String chinese){

        String url = SIMULATE_HOST + "/sys/simulate/convert";

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("chinese",chinese);
        String body = HttpRequest.of(url)
                .method(Method.GET)
                .addHeaders(this.getHeaderMap())
                .formStr(paramMap)
                .execute()
                .body();
        log.info("body ===> {}",body);
        return body;
    }

    /**
     * 获取QQ头像
     * @param qq
     * @return
     */
    public String fetchQQAvatar(String qq){
        String url = SIMULATE_HOST + "/sys/simulate/convert";

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("qq",qq);
        String body = HttpRequest.of(url)
                .method(Method.GET)
                .addHeaders(this.getHeaderMap())
                .formStr(paramMap)
                .execute()
                .body();
        log.info("body ===> {}",body);
        return body;
    }


    private Map<String,String> getHeaderMap(){
        Map<String,String> headerMap = new HashMap<>(4);
        headerMap.put("accessKey",accessKey);
        headerMap.put("nonce", RandomUtil.randomNumbers(4));
        String timestamp = String.valueOf(System.currentTimeMillis());
        headerMap.put("timestamp",timestamp);
        String sign = SignUtils.genSign(timestamp, secretKey);
        headerMap.put("sign", sign);
        return headerMap;
    }

}
