package com.unfbx.lambda;

import java.util.function.Consumer;

/**
 * @Description
 * 消费者 一个参数没结果 (参数)->void,
 * @Author Grt
 * @Date 2021-08-19
 */
public class ConsumerTest {

    public static <T> void test1(T t,Consumer<T> consumer){
        consumer.accept(t);
    }

    public static void main(String[] args) {
        test1(new User("a",16),
                (user) -> System.out.println(user));
    }
}
