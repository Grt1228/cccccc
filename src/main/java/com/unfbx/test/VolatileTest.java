package com.unfbx.test;

import static java.lang.Thread.sleep;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public class VolatileTest {

//    static boolean run = true;
    volatile static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                // ....
                System.out.println(1);
            }
        });
        t.start();
        sleep(10);
        run = false; // 线程t不会如预想的停下来
    }
}
