package com.unfbx.test.demo;

import com.unfbx.test.MyAtomicData;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public class Demo4Impl4 implements Demo4 {

    private MyAtomicData balance;

    public Demo4Impl4(Integer balance) {
        this.balance = new MyAtomicData(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.getData();
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
        balance.decrease(  amount);
    }

    public static void main(String[] args) {
        //无锁解决
        Demo4.demo(new Demo4Impl4(10000));
    }
}
