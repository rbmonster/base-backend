package com.sw.rpc.conf;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

    @Value("${sw.zookeeper.url:localhost}")
    private String url;


    @Bean
    public ZkClient zkClient() {
        return new ZkClient(url,2000);
    }

}
