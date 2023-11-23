package com.abing.api.config;

import com.abing.api.client.BingApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author CaptainBing
 * @Date 2023/11/12 21:31
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties("bing.client")
public class BingApiConfig {


    private String accessKey;

    private String secretKey;

    @Bean
    public BingApiClient bingApiClient(){
        return new BingApiClient();
    }


}
