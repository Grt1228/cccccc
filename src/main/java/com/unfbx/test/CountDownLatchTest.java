package com.unfbx.test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-16
 */
public class CountDownLatchTest {


    public static void main(String[] args) throws InterruptedException {
        SecureRandom random = new SecureRandom();
        CountDownLatch latch = new CountDownLatch(10);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String[] result = new String[10];
        for (int j = 0 ;j<10 ;j++){
            int k = j;
            executorService.submit(() -> {
                for (int i = 0 ; i<100;i++){
                    try {
                        Thread.sleep(random.nextInt(50));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    result[k] = ((i+1)+"%");
                    System.out.print("\r"+Arrays.toString(result));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n全部准备完毕，开始了.........");
        executorService.shutdown();
    }
}
