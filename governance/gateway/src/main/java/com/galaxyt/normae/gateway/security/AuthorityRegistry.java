package com.galaxyt.normae.gateway.security;

import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@ToString
public enum AuthorityRegistry {

    INSTANCE;


    private final ConcurrentHashMap<String, ConcurrentHashMap<String, AuthorityWrapper>> registry = new ConcurrentHashMap<>(16, 0.75f, 4);

    /**
     * 是否已经进行了初始化，默认 false
     */
    private final AtomicBoolean initialized = new AtomicBoolean(false);



    public void put(String clientId, ConcurrentHashMap<String, AuthorityWrapper> authoritys) {
        if (!registry.containsKey(clientId)) {
            registry.put(clientId, authoritys);
        }
    }


    public AuthorityWrapper get(String clientId, String methodType, String path) {
        if (registry.containsKey(clientId)) {
            return registry.get(clientId).get(String.format("%s:%s", methodType, path));
        }
        return null;
    }


    public boolean exists(String clientId) {
        return registry.containsKey(clientId);
    }

    public boolean isInitialized() {
        return initialized.get();
    }

    public void initialized() {
        initialized.set(true);
    }

}
