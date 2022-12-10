package com.example.demo.config;

import com.kx.c;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

public class CObjectPoolConfig extends GenericObjectPoolConfig<c> {
    public CObjectPoolConfig() {
        this.setMaxTotal(8);
        this.setMaxIdle(5);
        this.setMinIdle(1);
        this.setMaxWait(Duration.ofMinutes(3));
        this.setTimeBetweenEvictionRuns(Duration.ofMinutes(5));
        this.setTestWhileIdle(true);
        this.setJmxEnabled(false);
    }
}
