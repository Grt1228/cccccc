package com.unfbx.test.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author Grt 三个线程循环打印abcabcabcabcabcabc五次
 * @Date 2021-08-10
 */
@Slf4j(topic = "c.Demo2")
public class Demo2 {

    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        //创建三个休息室
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();
        new Thread(() -> {
            awaitSignal.print("a", a, b);
        }, "T1").start();
        new Thread(() -> {
            awaitSignal.print("b", b, c);
        }, "T2").start();
        new Thread(() -> {
            awaitSignal.print("c", c, a);
        }, "T3").start();
        //三个线程全部进入自己的休息室内,全部都是等待状态
        //主线程上锁
        awaitSignal.lock();

        try {
            //唤醒a
            a.signal();
        } finally {
            awaitSignal.unlock();
        }
    }
}


class AwaitSignal extends ReentrantLock {

    //循环次数
    private int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }

    /**
     * @param str     要打印的字符
     * @param current 当前休息室
     * @param next    下一个需要打印的休息室
     */
    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNum; i++) {
            lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }

    }
}
