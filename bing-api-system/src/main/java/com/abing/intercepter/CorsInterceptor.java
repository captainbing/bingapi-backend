package com.abing.intercepter;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域拦截器的配置
 * @author 阿炳亿点点帅
 */
public class CorsInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //表示接受任意域名的请求,也可以指定域名
        response.setHeader("Access-Control-Allow-Origin",
                request.getHeader("origin"));
        //该字段可选，是个布尔值，表示是否可以携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT,PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        // 如果当前请求中包含option请求  放行
        if (HttpMethod.OPTIONS.equals(request.getMethod())){
            return true;
        }
        return true;
    }
}
