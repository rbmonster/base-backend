package com.sw.rpc.register.impl;

import com.sw.rpc.domain.ServiceMateData;
import com.sw.rpc.helper.ZookeeperHelper;
import com.sw.rpc.register.Register;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZookeeperRegister implements Register {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZookeeperHelper zookeeperHelper;

    @Override
    public void checkBeforeRegister() {
        String rootPath = zookeeperHelper.getRootPath();
        if (!zkClient.exists(rootPath)){
            zkClient.createPersistent(rootPath);
        }
        String servicePath = zookeeperHelper.getServicePath();
        if (!zkClient.exists(servicePath)){
            zkClient.createPersistent(servicePath);
        }
    }

    @Override
    public void register(ServiceMateData serviceMateData) {
        zookeeperHelper.enrichMateData(serviceMateData);
        String nodePath = zookeeperHelper.getPath(serviceMateData);
        if (zkClient.exists(nodePath)){
            zkClient.delete(nodePath);
        }
        zkClient.createEphemeral(nodePath, serviceMateData);
    }


}
