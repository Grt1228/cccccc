package com.unfbx.test.demo;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public class Demo4Impl2 implements Demo4 {

    private Integer balance;

    public Demo4Impl2(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        this.balance -= amount;
    }

    public static void main(String[] args) {
        //加锁解决
        Demo4.demo(new Demo4Impl2(10000));
    }
}
