package com.unfbx.test.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public class Demo4Impl3 implements Demo4 {

    private AtomicInteger balance;

    public Demo4Impl3(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.get();
    }

    @Override
    public synchronized void withdraw(Integer amount) {
//        while (true){
//            int prev = balance.get();
//            int next = prev - amount;
//            if(balance.compareAndSet(prev,next)){
//                break;
//            }
//        }
        //简化写法
        balance.addAndGet(-1 * amount);
    }

    public static void main(String[] args) {
        //无锁解决
        Demo4.demo(new Demo4Impl3(10000));
    }
}
