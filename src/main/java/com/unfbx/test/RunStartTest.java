package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-18
 */
@Slf4j(topic = "c.RunStartTest")
public class RunStartTest {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.info("t1 running ");
        });
        t1.run();//主线程中执行run方法，没有启动新的线程

        t1.start();//启动新线程，新线程执行run方法
    }
}
