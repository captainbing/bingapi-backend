package com.abing.filter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.abing.api.utils.SignUtils;
import com.abing.dubbo.service.InterfaceInfoDubboService;
import com.abing.dubbo.service.UserDubboService;
import com.abing.model.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


/**
 * @Author abing
 * @Date 2023/7/25 16:26
 * @Description 自定义全局过滤器
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter {

    @Reference
    private InterfaceInfoDubboService interfaceInfoDubboService;

    @Reference
    private UserDubboService userDubboService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long interfaceCount = interfaceInfoDubboService.count();
        log.info("{}","Gateway Interceptor is come in :" + interfaceCount);
        long userServiceCount = userDubboService.count();
        log.info("{}","Gateway Interceptor is come in :" + userServiceCount);

        ServerHttpRequest request = exchange.getRequest();

        // 1.请求日志

        String id = request.getId();
        HttpMethod method = request.getMethod();
        String sourceAddress = request.getLocalAddress().getHostString();
        String remoteAddress = request.getRemoteAddress().getHostString();
        String path = request.getPath().value();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        log.info("请求唯一标识:{}",id);
        log.info("请求方法:{}",method);
        log.info("本地地址:{}",sourceAddress);
        log.info("远程地址:{}",remoteAddress);
        log.info("路径标识:{}",path);
        log.info("请求参数:{}",queryParams);

        ServerHttpResponse response = exchange.getResponse();

        // 2.访问控制 黑白名单
        // 3.用户鉴权
        boolean checkInvokeInterfaceAuth = checkInvokeInterfaceAuth(request);
        if (!checkInvokeInterfaceAuth){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // 4.请求的模拟接口是否存在
        // 5.请求转发调用模拟接口
        Mono<Void> filter = chain.filter(exchange);
        // 6.响应日志
        // 7.调用成功 接口次数加1
        // 8.调用失败 错误返回
        log.info("Gateway Interceptor is come out");
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();

//        return filter;

    }

    /**
     * 校验用户权限
     * @param request
     * @return
     */
    private boolean checkInvokeInterfaceAuth(ServerHttpRequest request){
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
//        String body = request.getHeader("body");
        if (StringUtils.isAnyEmpty(accessKey,nonce,timestamp,sign)){
            return false;
        }
        // TODO 获取accessKey
        User user = userDubboService.getUserByAccessKey(accessKey);

        if (!accessKey.equals(user.getAccessKey())){
            return false;
        }

        // TODO 校验nonce唯一字符串
        if (!(Long.parseLong(nonce) > 1000L)) {
            return false;
        }

        // TODO 校验过期时间 与当前时间不能超过5分钟
        LocalDateTime startTime = LocalDateTimeUtil.of(Long.parseLong(timestamp));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minus5MinutesTime = now.minusMinutes(5);
        int status = startTime.compareTo(minus5MinutesTime);
        // 大于0则超过五分钟
        if (status < 0){
            return false;
        }
        String serverSign = SignUtils.genSign(timestamp, user.getSecretKey());
        if (!sign.equals(serverSign)){
            return false;
        }
        return true;
    }
}
