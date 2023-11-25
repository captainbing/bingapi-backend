package com.abing.api.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 18:17
 * @Description
 */
public class SignUtils {

    /**
     * 生成签名
     * @param timestamp
     * @param secret
     * @return
     */
    public static String genSign(String timestamp, String secret){

        Digester digester = DigestUtil.digester(DigestAlgorithm.SHA256);
        String content = timestamp + "." + secret;
        return digester.digestHex(content);
    }

}
