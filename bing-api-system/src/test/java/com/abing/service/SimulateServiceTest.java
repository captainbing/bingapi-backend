package com.abing.service;

import com.abing.api.client.BingApiClient;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @Author CaptainBing
 * @Date 2023/11/24 17:52
 * @Description
 */
@SpringBootTest
class SimulateServiceTest {

//    @Resource
//    private BingApiClient bingApiClient;
    @Resource
    private Configuration configuration;

//    @Test
//    void convertChinese2Pinyin() {
//        String accessKey = "25ed1e0664ad5756d1d1f6adc85a4da4";
//        String secretKey = "230f4ddb9ae86665b0fa595a2957b2e7";
//        BingApiClient bingApiClient = new BingApiClient(accessKey,secretKey);
//        String chinese = "熊炳忠你好呀";
//        String pinyin = bingApiClient.convertChinese2Pinyin(chinese);
//        System.out.println("pinyin = " + pinyin);
//
//    }

    @Test
    void convertChinese2Pinyin() throws IOException, TemplateException {
        Template template = configuration.getTemplate("EmailTemplate.html.ftl");
        FileWriter writer = new FileWriter("EmailTemplate.html");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        template.process(strings,writer);
    }
}