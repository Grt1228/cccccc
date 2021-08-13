package com.unfbx.test.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author Grt 三个线程循环打印abcabcabcabcabcabc五次
 * @Date 2021-08-10
 */
@Slf4j(topic = "c.Demo1")
public class Demo1 {

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1,5);
        new Thread(() -> {waitNotify.print("a",1,2);},"T1").start();
        new Thread(() -> {waitNotify.print("b",2,3);},"T2").start();
        new Thread(() -> {waitNotify.print("c",3,1);},"T3").start();
    }
}


class WaitNotify {
    //等待标记，
    private int flag;
    //循环次数
    private int loopNum;

    public WaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    /**
     * @param str      要打印的
     * @param waitFlag 等待标记
     * @param nextFlag 下一个打印标记
     */
    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }

    }
}
