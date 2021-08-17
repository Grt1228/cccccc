package com.unfbx.test.poll;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-16
 */
@Slf4j(topic = "c.JdkPoolTest")
public class JdkPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(r, "JdkPoolTest-pool-thread ");
            return t;
        });

        threadPool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("running 1................");
        });
        threadPool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("running 2................");
        });
        threadPool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("running 3................");
        });
        threadPool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("running 4................");
        });

        threadPool.shutdown();
    }
}
