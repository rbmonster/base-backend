package com.sw.rpc;

import com.sw.rpc.annotation.SwRpcConsumer;
import com.sw.rpc.annotation.SwRpcProvider;
import com.sw.rpc.cache.RpcProviderCache;
import com.sw.rpc.domain.ServiceMateData;
import com.sw.rpc.netty.NettyServiceContainer;
import com.sw.rpc.register.Register;
import com.sw.rpc.service.FetchServiceMateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class RpcBootStater implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Register register;

    @Autowired
    private NettyServiceContainer nettyServiceContainer;

    @Autowired
    private FetchServiceMateDataService fetchServiceMateDataService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (Objects.isNull(contextRefreshedEvent.getApplicationContext().getParent())) {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            checkBeforeRegister();
            registerContext(applicationContext);
        }
    }

    private void checkBeforeRegister(){
        register.checkBeforeRegister();
    }

    private void registerContext(ApplicationContext applicationContext) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SwRpcProvider.class);
        if (beansWithAnnotation.size() >0) {
            for (Object obj : beansWithAnnotation.values()) {
                Class<?> clazz = obj.getClass();
                SwRpcProvider annotation = clazz.getAnnotation(SwRpcProvider.class);
                ServiceMateData serviceMateData = new ServiceMateData(annotation.interfaceClass().getSimpleName(), annotation.version());
                if (annotation.interfaceClass() == void.class) {
                    serviceMateData.setClassName(clazz.getSimpleName());
                }
                RpcProviderCache.put(annotation.interfaceClass(), obj);
                register.register(serviceMateData);
            }
        }

        // run netty server
        new Thread(() -> nettyServiceContainer.start()).start();
    }


    private void subscribeService(ApplicationContext applicationContext){
        fetchServiceMateDataService.fetch();
    }
}
