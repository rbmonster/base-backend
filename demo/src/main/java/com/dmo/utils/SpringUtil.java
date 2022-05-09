package com.dmo.utils;

import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

public class SpringUtil {

    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext){
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
        }

    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static DataSource getDataSource() {
        DataSource dataSource = getBean("dataSource", DataSource.class);
        return dataSource;
    }

    public static String[] getBeanNamesForType(Class clz){
        return getApplicationContext().getBeanNamesForType(clz);
    }

    public static boolean containsBean(Class clazz) {
        try {
            return getApplicationContext().getBean(clazz) != null;
        } catch (Exception ex) {
            return false;
        }
    }
}