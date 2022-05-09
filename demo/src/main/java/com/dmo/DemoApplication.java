package com.dmo;

import com.dmo.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.setAllowBeanDefinitionOverriding(true);
        ApplicationContext applicationContext = application.run(args);
        TimeUnit.SECONDS.sleep(100);
        SpringUtil.setApplicationContext(applicationContext);
    }

}
