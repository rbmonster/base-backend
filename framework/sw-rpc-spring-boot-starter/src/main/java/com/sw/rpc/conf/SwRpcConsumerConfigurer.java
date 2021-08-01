//package com.sw.rpc.conf;
//
//import com.sw.rpc.annotation.SwRpcConsumer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.reflect.Field;
//import java.util.Iterator;
//import java.util.Map;
//
//@Slf4j
//public class SwRpcConsumerConfigurer implements ApplicationContextAware,BeanFactoryPostProcessor {
//
//    private ApplicationContext context;
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
//            throws BeansException {
//        postProcessRpcConsumerBeanFactory(beanFactory, (BeanDefinitionRegistry)beanFactory);
//    }
//
//    private void postProcessRpcConsumerBeanFactory(ConfigurableListableBeanFactory beanFactory, BeanDefinitionRegistry beanDefinitionRegistry) {
//        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
//        int len = beanDefinitionNames.length;
//        for (int i = 0; i < len; i++) {
//            String beanDefinitionName = beanDefinitionNames[i];
//            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
//            ClassLoader classLoader = beanFactory.getBeanClassLoader();
//            String beanClassName = beanDefinition.getBeanClassName();
//            if (beanClassName != null) {
//                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, classLoader);
//                ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
//                    @Override
//                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
//                        parseField(field);
//                    }
//                });
//            }
//
//        }
//
//        Iterator<Map.Entry<String, BeanDefinition>> it = beanFactory.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, BeanDefinition> entry = it.next();
//            if (context.containsBean(entry.getKey())) {
//                throw new IllegalArgumentException("Spring context already has a bean named " + entry.getKey());
//            }
//            beanDefinitionRegistry.registerBeanDefinition(entry.getKey(), entry.getValue());
//            log.info("register OrcRpcConsumerBean definition: {}", entry.getKey());
//        }
//
//    }
//
//    private void parseField(Field field) {
//        // 获取所有OrcRpcConsumer注解
//        SwRpcConsumer swRpcConsumer = field.getAnnotation(SwRpcConsumer.class);
//        if (swRpcConsumer != null) {
//            // 使用field的类型和OrcRpcConsumer注解一起生成BeanDefinition
//            OrcRpcConsumerBeanDefinitionBuilder beanDefinitionBuilder = new OrcRpcConsumerBeanDefinitionBuilder(field.getType(), orcRpcConsumer);
//            BeanDefinition beanDefinition = beanDefinitionBuilder.build();
//            beanDefinitions.put(field.getName(), beanDefinition);
//        }
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context = applicationContext;
//    }
//}
