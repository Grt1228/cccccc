package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-04
 */
@Slf4j(topic = "c.FutureTaskTest")
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask(() -> {
            log.info("线程执行");
            return "线程返回值";
        });

        new Thread(futureTask,"T1").start();
        String res = futureTask.get();
        log.info(res);
    }
}
