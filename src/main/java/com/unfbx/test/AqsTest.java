package com.unfbx.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-16
 */
public class AqsTest {
}

/**
 * 不可重入
 */
class MyLock implements Lock{

    class MySync extends AbstractQueuedSynchronizer{
        /**
         * 尝试获得锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                //设置当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 解锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            //volatile修饰-》有写屏障保证指令顺序
            setState(0);
            return true;
        }

        /**
         * 是否持有独占锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }


    private MySync sync = new MySync();

    /**
     * 不成功会进入等待队列等待
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 加锁可打断的
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁 一次不成功返回false
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    /**
     * 创建条件变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
