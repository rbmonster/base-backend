package com.sw.rpc.service;

import com.sw.rpc.domain.ServiceMateData;
import com.sw.rpc.helper.ZookeeperHelper;
import com.sw.rpc.netty.ConnectManageCache;
import com.sw.rpc.register.cache.ServiceMateDataCache;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FetchServiceMateDataService {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZookeeperHelper zookeeperHelper;

    @Autowired
    private ConnectManageCache connectManageCache;

    public void fetch() {
        String rootPath = zookeeperHelper.getRootPath();
        List<String> children = zkClient.getChildren(rootPath);

        String servicePath = zookeeperHelper.getServicePath();
        String delimiter = zookeeperHelper.getServicePathDelimiter();
        List<ServiceMateData> serviceMateDataList = new LinkedList<>();
        children.stream().filter(path -> !path.startsWith(servicePath))
                .forEach(path -> {
                    List<String> childrenList = zkClient.getChildren(zookeeperHelper.convertPath(rootPath, path));
                    childrenList.forEach(childrenPath -> {
                        String[] split = childrenPath.split("-");
                        if (split.length !=2 ){
                            log.warn("zookeeper path may be error, path: {}", path);
                        } else {
                            Object obj = zkClient.readData(zookeeperHelper.convertPath(rootPath, path, childrenPath));
                            log.info("fetch zookeeper path:{}, data:{}", path, obj);
                            if (obj instanceof ServiceMateData) {
                                ServiceMateData serviceMateData = (ServiceMateData) obj;
                                ServiceMateDataCache.put(split[1], serviceMateData);
                                serviceMateDataList.add(serviceMateData);
                            }
                        }
                    });

                 });
        initChannel(serviceMateDataList);
    }

    private void initChannel(List<ServiceMateData> serviceMateDataList) {
        connectManageCache.createChannel(serviceMateDataList);
    }
}
