package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-12
 */
@Slf4j(topic = "c.AtomicClassTest")
public class AtomicClassTest {

    volatile static int countDemo = 0;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(1000);

        List<Thread> t1s = new ArrayList<>();
        List<Thread> t2s = new ArrayList<>();

        for (int i = 0 ; i< 10000 ;i++){
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDemo++;
                count.addAndGet(1);

            });
            t1s.add(t1);
            Thread t2 = new Thread(() -> {
                countDemo--;
                count.addAndGet(-1);

            });
            t2s.add(t2);

        }
        t1s.forEach(Thread::start);
        t2s.forEach(Thread::start);
//        for (int i = 0; i<10000 ;i++){
//            t1s.get(i).join();
//            t2s.get(i).join();
//        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }

        log.info("count is : {}",count.get());
        log.info("countDemo is : {}",countDemo);
    }
}
