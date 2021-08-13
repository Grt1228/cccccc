package com.unfbx.test.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author Grt 三个线程循环打印abcabcabcabcabcabc五次
 * @Date 2021-08-10
 */
@Slf4j(topic = "c.Demo3")
public class Demo3 {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        ParkUnPark parkUnPark = new ParkUnPark(5);

        t1 = new Thread(() -> {
            parkUnPark.print("a", t2);
        }, "T1");
        t2 = new Thread(() -> {
            parkUnPark.print("b", t3);
        }, "T2");
        t3 = new Thread(() -> {
            parkUnPark.print("c", t1);
        }, "T3");
        t1.start();
        t2.start();
        t3.start();
        //初始化唤醒T1
        LockSupport.unpark(t1);
    }
}


class ParkUnPark {

    //循环次数
    private int loopNum;

    public ParkUnPark(int loopNum) {
        this.loopNum = loopNum;
    }

    /**
     * @param str  要打印的字符
     * @param next 下一个需要运行的线程
     */
    public void print(String str, Thread next) {
        for (int i = 0; i < loopNum; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }

    }
}
