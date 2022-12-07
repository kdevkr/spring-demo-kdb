package com.example.demo.config;

import com.kx.c;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KdbConfig {

    @Bean
    public ObjectPool<c> cObjectPool() {
        PooledObjectFactory<c> cPooledObjectFactory = new CPooledObjectFactory("localhost", 5555);
        GenericObjectPoolConfig<c> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(1);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setJmxEnabled(false);

        GenericObjectPool<c> genericObjectPool = new GenericObjectPool<>(cPooledObjectFactory, poolConfig);
        try {
            genericObjectPool.preparePool();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genericObjectPool;
    }

}
