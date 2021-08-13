package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * @Description 转账
 * @Author Grt
 * @Date 2021-08-05
 */
@Slf4j(topic = "c.ExerciseTransfer")
public class ExerciseTransfer {

    static SecureRandom random = new SecureRandom();

    public static int random(){
        return random.nextInt(100) +1;
    }
    public static void main(String[] args) throws InterruptedException {

        int[] array = {1,4,3,5,6,7,9,10,12,18,17,3,4,8};

        Account a = new Account(1000);
        Account b = new Account(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, random());
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, random());
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        //总金额

        log.info("转账后总金额：{}",a.getMoney()+b.getMoney());

}


}

class Account {

    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Account(int money) {
        this.money = money;
    }

    public void transfer(Account target, int money) {
        synchronized (Account.class){
            if (this.money >= money) {
                this.setMoney(this.money - money);
                target.setMoney(target.getMoney() + money);
            }
        }
    }
}
