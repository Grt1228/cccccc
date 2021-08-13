package com.unfbx.test.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public interface Demo4 {

    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    static void demo(Demo4 account) {
        long start = System.nanoTime();
        List<Thread> ts = new ArrayList<>(1000);

        //创建1000个线程，每个线程取款10
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.withdraw(10);
            });
            ts.add(thread);
        }
        //启动线程
        ts.forEach(Thread::start);
        //等待线程全部执行完毕
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost : " + (end - start) / 1000_00 + "ms");

    }
}
