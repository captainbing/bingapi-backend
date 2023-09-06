package com.abing.filter;

import com.abing.model.domain.Quote;
import com.abing.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @Author abing
 * @Date 2023/7/25 16:26
 * @Description 自定义全局过滤器
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter {

    @Reference
    private InterfaceInfoService interfaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long count = interfaceInfoService.count();
        log.info("{}","Gateway Interceptor is come in" + count);
        return chain.filter(exchange);

//        return exchange.getResponse().setComplete();
    }
}
