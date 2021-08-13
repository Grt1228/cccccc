package com.unfbx.test.demo;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
public class Demo4Impl1 implements Demo4 {

    private Integer balance;

    public Demo4Impl1(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer amount) {
        this.balance -= amount;
    }

    public static void main(String[] args) {
        //存在线程安全问题
        Demo4.demo(new Demo4Impl1(10000));
    }
}
