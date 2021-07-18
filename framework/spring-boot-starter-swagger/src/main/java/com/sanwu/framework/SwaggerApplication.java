package com.sanwu.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 * @Description:
 * SwaggerApplication
 * </pre>
 *
 * @version v1.0
 * @ClassName: SwaggerApplication
 * @Author: sanwu
 * @Date: 2020/12/26 16:05
 */
@SpringBootApplication
@EnableSwagger2
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
