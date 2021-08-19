package com.unfbx.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-19
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        //加锁状态置为1（可重入锁状态累加state++）
        lock.lock();
        try {

        }finally {
            //唤醒阻塞线程的一个过程，状态state置为0（可重入锁累减，为0是释放锁）
            //非公平锁的情况下  唤醒后有竞争失败的情况，将会重新被阻塞
            lock.unlock();

        }
    }
}
