package com.sw.rpc.conf;

import com.sw.rpc.annotation.SwRpcConsumer;
import com.sw.rpc.netty.NettyClient;
import com.sw.rpc.proxy.ConsumerProxy;
import com.sw.rpc.register.cache.ClientServiceDiscoveryCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Optional;

public class SwRpcConsumerBeanInstanceProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getFields())
                .filter(field -> field.getAnnotation(SwRpcConsumer.class) != null)
                .forEach(field -> {
                    SwRpcConsumer rpcConsumer = field.getAnnotation(SwRpcConsumer.class);
                    Optional<Object> obj = ClientServiceDiscoveryCache.get(rpcConsumer.interfaceClass());
                    if (obj.isPresent()) {
                        ReflectionUtils.setField(field, bean, obj.get());
                    } else {
                        Object proxy = createProxy(rpcConsumer);
                        ReflectionUtils.setField(field, bean, proxy);
                        ClientServiceDiscoveryCache.put(rpcConsumer.interfaceClass(), proxy);
                    }
                });
        return bean;
    }

    private Object createProxy(SwRpcConsumer swRpcConsumer) {
        Class<?> clazz = swRpcConsumer.interfaceClass();
        NettyClient nettyClient = applicationContext.getBean(NettyClient.class);
        return ConsumerProxy.createProxy(swRpcConsumer, clazz, nettyClient);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
