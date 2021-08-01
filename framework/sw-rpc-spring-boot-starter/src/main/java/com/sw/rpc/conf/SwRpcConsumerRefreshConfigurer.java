//package com.sw.rpc.conf;
//
//import com.sw.rpc.annotation.SwRpcConsumer;
//import org.springframework.beans.BeansException;
//import org.springframework.boot.SpringApplicationRunListener;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import java.util.Map;
//
//public class SwRpcConsumerRefreshConfigurer implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
//        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SwRpcConsumer.class);
////        beansWithAnnotation.fo
//    }
//}
