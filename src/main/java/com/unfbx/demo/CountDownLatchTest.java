package com.unfbx.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-04
 */
public class CountDownLatchTest {
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                try {
                    long wait = (long) (Math.random() * 1000);
                    Thread.sleep(wait);
                    System.out.println(wait);
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // wait for the latch to be decremented by the fast threads
        try {
            latch.await();
            System.out.println("Fast Thead End");
            es.shutdownNow();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
