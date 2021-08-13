package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-05
 */
@Slf4j(topic = "c.ThreadNumberTest")
public class ThreadNumberTest {

    public static void main(String[] args) {
        Number n1 = new Number();
        Number n2 = new Number();
        new Thread(()->{
            try {
                n1.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{ n2.b(); }).start();
    }

}

@Slf4j(topic = "c.Number")
class Number{
    public synchronized void a() throws InterruptedException {
        Thread.sleep(100);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
}
