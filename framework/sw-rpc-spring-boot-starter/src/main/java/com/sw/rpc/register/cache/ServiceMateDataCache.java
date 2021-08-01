package com.sw.rpc.register.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sw.rpc.domain.ServiceMateData;

import java.util.List;
import java.util.Optional;

public class ServiceMateDataCache {

    private static final Cache<String, ServiceMateData> cache = Caffeine.newBuilder()
            .build();

    public static void put(String name, ServiceMateData ServiceMateData) {
        cache.put(name, ServiceMateData);
    }

    public static Optional<ServiceMateData> get(String name) {
        return Optional.ofNullable(cache.get(name, t -> null));
    }
}
