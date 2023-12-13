package com.abing.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.abing.api.utils.SignUtils;
import com.abing.dubbo.service.InterfaceInfoDubboService;
import com.abing.dubbo.service.UserDubboService;
import com.abing.dubbo.service.UserInterfaceInfoDubboService;
import com.abing.manager.RedisLimiterManager;
import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;


/**
 * @Author abing
 * @Date 2023/7/25 16:26
 * @Description 自定义全局过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Reference
    private InterfaceInfoDubboService interfaceInfoDubboService;
    @Reference
    private UserDubboService userDubboService;
    @Reference
    private UserInterfaceInfoDubboService userInterfaceInfoDubboService;

    /**
     * redisson限流
     */
    private final RedisLimiterManager redisLimiterManager;

    /**
     * 白名单
     */
    @Value("${ipcontrol.white}")
    public List<String> IP_WHITE_LIST;
//    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 1.打印请求日志
        String sourceAddress = printRequestLog(request);

        // 2.访问控制 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)){
            return handleNoAuth(response,HttpStatus.NOT_ACCEPTABLE);
        }
        // 3.用户鉴权
        User user = checkInvokeInterfaceAuth(request);
        if (user == null){
            return handleNoAuth(response,HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        // redisson限流操作
        boolean canOp = redisLimiterManager.doRateLimit(user.getId());
        if (!canOp){
            return handleNoAuth(response,HttpStatus.TOO_MANY_REQUESTS);
        }
        // 4.请求的模拟接口是否存在
        InterfaceInfo interfaceInfo = existsSimulateInterface(request);
        if (interfaceInfo == null){
            return handleNoAuth(response,HttpStatus.NOT_FOUND);
        }
        // 6.响应日志
        return handleResponse(exchange, chain,user.getId(),String.valueOf(interfaceInfo.getId()));
    }

    /**
     * 校验模拟接口是否存在
     * @param request
     */
    private InterfaceInfo existsSimulateInterface(ServerHttpRequest request) {

        String url = request.getURI().toString();
        String method = request.getMethod().toString();
        int index = !url.contains("?") ? url.length() : url.indexOf("?");
        String realUrl = url.substring(0, index);

        InterfaceInfo interfaceInfo = interfaceInfoDubboService.getInterfaceInfoByUrl(realUrl);
        if (interfaceInfo == null) {
            return null;
        }
        if (!interfaceInfo.getMethod().equalsIgnoreCase(method)){
            return null;
        }

        return interfaceInfo;

    }

    /**
     * 打印请求日志
     * @param request
     */
    private String printRequestLog(ServerHttpRequest request) {

        String id = request.getId();
        String uri = request.getURI().toString();
        HttpMethod method = request.getMethod();
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        String remoteAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        String path = request.getPath().value();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        log.info("请求唯一标识:{}",id);
        log.info("请求uri:{}",uri);
        log.info("请求方法:{}",method);
        log.info("本地地址:{}",sourceAddress);
        log.info("远程地址:{}",remoteAddress);
        log.info("路径标识:{}",path);
        log.info("请求参数:{}",queryParams);

        return sourceAddress;

    }


    /**
     * 装饰器模式打印响应日志
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,String userId,String interfaceInfoId){

        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatus statusCode = originalResponse.getStatusCode();
            if(statusCode == HttpStatus.OK){
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    /**
                     * 等转发的方法调用完才会执行  promise
                     * @param body the body content publisher
                     * @return
                     */
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                //释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                // 构建日志
                                String data = new String(content, StandardCharsets.UTF_8);//data

                                JSONObject jsonObject = JSONUtil.parseObj(data);
                                Integer code = (Integer) jsonObject.get("code");
                                // 7.调用成功 接口次数加1
                                if (HttpStatus.OK.value() == code){
                                    Boolean invokeCountPlusOneStatus = userInterfaceInfoDubboService.invokeCountPlusOne(userId, interfaceInfoId);
                                    if (!invokeCountPlusOneStatus){
                                        throw new RuntimeException("可用次数减少失败!!!");
                                    }
                                }else {
                                    // 8.调用失败 错误返回
                                    throw new RuntimeException("调用失败 请重试!!!");
                                }
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 5.请求转发调用模拟接口
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }

    }

    /**
     * 拒绝请求
     * @param response
     * @return
     */
    private Mono<Void> handleNoAuth(ServerHttpResponse response,HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * 校验用户权限
     * @param request
     * @return
     */
    private User checkInvokeInterfaceAuth(ServerHttpRequest request){
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
//        String body = request.getHeader("body");
        if (StringUtils.isAnyEmpty(accessKey,nonce,timestamp,sign)){
            return null;
        }
        User user = userDubboService.getUserByAccessKey(accessKey);

        if (!accessKey.equals(user.getAccessKey())){
            return null;
        }

        // 校验nonce唯一字符串,取redis中存入的nonce唯一值
        String nonceValue = redisLimiterManager.getNonce(nonce);
        // redis中存在值，则为重复请求，取消调用
        if (StringUtils.isNotEmpty(nonceValue)) {
            return null;
        }
        redisLimiterManager.setNonceFor5Min(nonce);
        // 校验过期时间 与当前时间不能超过5分钟
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5;
        long historyTime = Long.parseLong(timestamp) / 1000;
        if ((currentTime - historyTime) > FIVE_MINUTES){
            return null;
        }
        String serverSign = SignUtils.genSign(timestamp, user.getSecretKey());
        if (!sign.equals(serverSign)){
            return null;
        }
        return user;
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
