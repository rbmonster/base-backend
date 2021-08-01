package com.sw.rpc.netty;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sw.rpc.domain.ServiceMateData;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConnectManageCache {

    @Autowired
    private NettyClient nettyClient;

    private static final Cache<String, Channel> cache = Caffeine.newBuilder()
            .build();

    public static void put(String serviceId, Channel channel) {
        cache.put(serviceId, channel);
    }

    public static Optional<Channel> get(String serviceId) {
        return Optional.ofNullable(cache.get(serviceId, t -> null));
    }


    public void createChannel(List<ServiceMateData> serviceMateDataList) {
        Map<String, List<ServiceMateData>> map = serviceMateDataList.stream()
                .collect(Collectors.groupingBy(ServiceMateData::getServiceId));

        map.forEach((key, value) -> {
                   Optional<Channel> optional = get(key);
                   if (!optional.isPresent()) {
                       Optional<InetSocketAddress> address = value.stream().map(serviceMateData -> new InetSocketAddress(serviceMateData.getHost(), serviceMateData.getPort()))
                               .findAny();
                       if (address.isPresent()){
                           try {
                               Channel channel = nettyClient.doConnect(address.get());
                               put(key, channel);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }
               });

    }
}
