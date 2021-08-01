package com.sw.rpc.proxy;

import com.alibaba.fastjson.JSON;
import com.sw.rpc.annotation.SwRpcConsumer;
import com.sw.rpc.domain.SwRpcRequest;
import com.sw.rpc.domain.SwRpcResponse;
import com.sw.rpc.netty.NettyClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class ConsumerProxy {

    public static <T> T createProxy(SwRpcConsumer swRpcConsumer, Class<T> target, NettyClient nettyClient) {
        return (T)Proxy.newProxyInstance(
                target.getClassLoader(),
                target.getInterfaces(),
                (proxy, method, args) -> {
                    Class<?> clazz = swRpcConsumer.interfaceClass();
                    SwRpcRequest request = new SwRpcRequest();
                    request.setId(UUID.randomUUID().toString());
                    request.setClazz(clazz);
                    request.setMethodName(method.getName());
                    request.setParameters(args);
                    Class<?>[] classes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
                    request.setParameterTypes(classes);
                    log.info("invoke target method:{}, parameter: {}", method.getName(), request);
                    Object send = nettyClient.send(request);
                    SwRpcResponse swRpcResponse = JSON.parseObject(String.valueOf(send), SwRpcResponse.class);
                    if (Objects.isNull(swRpcResponse) || !swRpcResponse.isSuccess()) {
                        log.error("request faile :{}", swRpcConsumer);
                        return null;
                    }
                    return swRpcResponse.getData();
        });
    }
}
