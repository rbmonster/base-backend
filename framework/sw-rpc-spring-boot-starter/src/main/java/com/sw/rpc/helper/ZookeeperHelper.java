package com.sw.rpc.helper;

import com.sw.rpc.domain.ServiceMateData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperHelper {


    private static final String SERVICE_PATH_DELIMITER = "/";

    private static final String ROOT_NODE = "sanwu";

    @Value("${spring.application.name}")
    private String serviceId;

    @Value("${sw.netty.host:localhost}")
    private String host;

    @Value("${sw.netty.port:1111}")
    private int port;

    public String getRootPath() {
        return SERVICE_PATH_DELIMITER + ROOT_NODE;
    }

    public String getServicePath() {
        return SERVICE_PATH_DELIMITER + ROOT_NODE + SERVICE_PATH_DELIMITER + serviceId;
    }

    public String getPath(ServiceMateData serviceMateData) {
        return SERVICE_PATH_DELIMITER + ROOT_NODE + SERVICE_PATH_DELIMITER +serviceMateData.getServiceId()
                + SERVICE_PATH_DELIMITER + serviceMateData.getVersion() +SERVICE_PATH_DELIMITER +serviceMateData.getClassName();
    }

    public String getServicePathDelimiter() {
        return SERVICE_PATH_DELIMITER;
    }

    public void enrichMateData(ServiceMateData serviceMateData) {
        serviceMateData.setServiceId(serviceId);
        serviceMateData.setHost(host);
        serviceMateData.setPort(port);
    }
}
