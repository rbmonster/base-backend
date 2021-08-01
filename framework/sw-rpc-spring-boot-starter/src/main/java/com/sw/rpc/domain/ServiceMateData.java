package com.sw.rpc.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册中心节点信息
 */
@Data
public class ServiceMateData implements Serializable {

    private static final long serialVersionUID = -2134047184543835188L;

    private String className;

    private String version;

    private String serviceId;

    private String host;

    private int port;

    public ServiceMateData(String className, String version) {
        this.className = className;
        this.version = version;
    }
}
