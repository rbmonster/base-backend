package com.sw.rpc.conf;

import com.alibaba.fastjson.JSON;
import com.sw.rpc.annotation.SwRpcConsumer;
import com.sw.rpc.domain.SwRpcRequest;
import com.sw.rpc.domain.SwRpcResponse;
import com.sw.rpc.netty.NettyClient;
import com.sw.rpc.proxy.ConsumerProxy;
import com.sw.rpc.register.cache.ClientServiceDiscoveryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class SwRpcConsumerBeanInstanceProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(SwRpcConsumer.class) != null)
                .forEach(field -> {
                    SwRpcConsumer rpcConsumer = field.getAnnotation(SwRpcConsumer.class);
                    Optional<Object> obj = ClientServiceDiscoveryCache.get(rpcConsumer.interfaceClass());
                    if (obj.isPresent()) {
                        ReflectionUtils.setField(field, bean, obj.get());
                    } else {
                        Object proxy = createProxy(rpcConsumer, bean, field);
                        ClientServiceDiscoveryCache.put(rpcConsumer.interfaceClass(), proxy);
                    }
                });
        return bean;
    }

    private <T> T createProxy(SwRpcConsumer swRpcConsumer, Object target, Field field) {
        Class<?> clazz = swRpcConsumer.interfaceClass();
        NettyClient nettyClient = applicationContext.getBean(NettyClient.class);
        T t = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                (proxy, method, args) -> {
                    SwRpcRequest request = new SwRpcRequest();
                    request.setId(UUID.randomUUID().toString());
                    request.setClazz(swRpcConsumer.interfaceClass());
                    request.setMethodName(method.getName());
                    request.setParameters(args);
                    Class<?>[] classes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
                    request.setParameterTypes(classes);
                    log.info("invoke target method:{}, parameter: {}", method.getName(), request);
                    Object send = nettyClient.send(request);
                    SwRpcResponse swRpcResponse = JSON.parseObject(String.valueOf(send), SwRpcResponse.class);
                    if (Objects.isNull(swRpcResponse) || !swRpcResponse.isSuccess()) {
                        log.error("request faile :{}", swRpcConsumer);
                        return "";
                    }
                    return JSON.parseObject(JSON.toJSONString(swRpcResponse.getData()), method.getReturnType());
                });
        field.setAccessible(true);
        ReflectionUtils.setField(field, target, t);
        return t;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
