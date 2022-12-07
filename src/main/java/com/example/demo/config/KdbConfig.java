package com.example.demo.config;

import com.kx.c;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KdbConfig {

    @Bean
    public ObjectPool<c> cObjectPool() {
        PooledObjectFactory<c> cPooledObjectFactory = PoolUtils.synchronizedPooledFactory(new CPooledObjectFactory("localhost", 5555));
        GenericObjectPool<c> genericObjectPool = new GenericObjectPool<>(cPooledObjectFactory);
        genericObjectPool.setMaxTotal(8);
        genericObjectPool.setMaxIdle(8);
        genericObjectPool.setMinIdle(1);
        try {
            genericObjectPool.preparePool();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PoolUtils.synchronizedPool(genericObjectPool);
    }

}
