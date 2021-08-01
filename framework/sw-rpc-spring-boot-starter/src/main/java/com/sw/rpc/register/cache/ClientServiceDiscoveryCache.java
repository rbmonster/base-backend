package com.sw.rpc.register.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;

public class ClientServiceDiscoveryCache {

    private static final Cache<Class<?>, Object> cache = Caffeine.newBuilder()
            .build();


    public static void put(Class<?> clazz, Object obj) {
        cache.put(clazz, obj);
    }

    public static Optional<Object> get(Class<?> clazz) {
         return Optional.ofNullable(cache.get(clazz, t -> null));
    }
}
