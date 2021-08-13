package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @Description
 * 卖票
 * @Author Grt
 * @Date 2021-08-05
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    static SecureRandom random = new SecureRandom();

    public static int random(){
        return random.nextInt(10) +1;
    }

    public static void main(String[] args) throws InterruptedException {
        //总共5000个票
        TicketWindow window = new TicketWindow(100);

        List<Integer> sellList = new Vector<>();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0 ; i<4000;i++){
            Thread thread = new Thread(() -> {
                int sell = window.sell(random());
                try {
                    Thread.sleep(random());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sellList.add(sell);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread t : threads){
            t.join();
        }


        //等待线程执行结束再打印
        //统计卖出票数和剩余票数
        log.info("剩余票数：{}",window.getCount());
        log.info("卖出票数：{}",sellList.stream().mapToInt(i -> i ).sum());



    }
}

class TicketWindow{
    //剩余票数
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public synchronized int sell(int num){
        if(count >= num){
            this.count -= num;
            return num;
        }
        return 0;
    }

    public int getCount() {
        return count;
    }
}
