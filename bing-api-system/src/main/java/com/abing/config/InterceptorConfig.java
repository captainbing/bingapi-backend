package com.abing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 阿炳亿点点帅
 * 配置生效的拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置：前端是8000端口，后端是9527
        //要允许8080访问接口服务
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000")
                .allowCredentials(true)
                .allowedMethods("GET","POST", "HEAD", "PUT","PATCH", "DELETE", "OPTIONS")
                .allowedOriginPatterns("*")
                .maxAge(3600);
    }
}