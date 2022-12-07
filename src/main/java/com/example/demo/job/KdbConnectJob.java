package com.example.demo.job;

import com.kx.c;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.net.SocketException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class KdbConnectJob {
    private final ObjectPool<c> cObjectPool;

    public KdbConnectJob(ObjectPool<c> cObjectPool) {
        this.cObjectPool = cObjectPool;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void connectSchedule() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);
        while (latch.getCount() > 0) {
            executorService.submit(this::connect);
            latch.countDown();
        }
    }

    public void connect() {

        c c = null;
        try {
            c = cObjectPool.borrowObject();
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            c.k("1");
            Object o = c.k(".j.j (`current`total!(.z.w;count .z.W))");
            stopWatch.stop();
            log.info("o: {}, {} ms", new String((char[]) o), stopWatch.getTotalTimeMillis());
            cObjectPool.returnObject(c);
        } catch (Throwable e) {
            if (c != null) {
                try {
                    cObjectPool.invalidateObject(c);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
            if (e instanceof SocketException se) {
                String message = se.getMessage();
                if (message != null && message.contains("Connection reset")) {
                    log.error(e.getMessage());
                }
                // ignored
            } else {
                e.printStackTrace();
            }
        }
    }
}
