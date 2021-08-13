package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-06
 */
@Slf4j(topic = "c.TestMarkWord")
public class TestMarkWord {

    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();


        log.info(String.valueOf(ClassLayout.parseInstance(dog).toPrintable()));
        Thread.sleep(4000);
        log.info(String.valueOf(ClassLayout.parseInstance(new Dog()).toPrintable()));
        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());
    }
}

class Dog{

}
