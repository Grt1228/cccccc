package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-18
 */
@Slf4j(topic = "c.ParkTest")
public class ParkTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("t1 park ................");
            LockSupport.park();
            log.info("park end .............");
        });
        t1.start();
        Thread.sleep(2000);
        log.info(" unpark .......");

        LockSupport.unpark(t1);
        log.info(" all end");
    }
}
