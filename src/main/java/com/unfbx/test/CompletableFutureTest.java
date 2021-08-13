package com.unfbx.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-04
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture futures = new CompletableFuture();


        new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("running");
                    }
                    futures.complete("for循环外边任务执行完成了，返回结果：布达拉宫");
                }
        ).start();

        System.out.println(futures.get());

    }
}
