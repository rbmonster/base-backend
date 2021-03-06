package com.sanwu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: MongoSpringApplication
 * @Author: sanwu
 * @Date: 2021/7/21 22:16
 */
//@EnableMongoRepositories
@SpringBootApplication
public class MongoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSpringApplication.class, args);
    }


}
