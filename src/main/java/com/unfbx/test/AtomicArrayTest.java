package com.unfbx.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-13
 */
public class AtomicArrayTest {

    public static void main(String[] args) {
        demo(
                () -> new int[10],
                (array) -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );
        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                (array) -> System.out.println(array)
        );
    }

    /**
     * // supplier 提供者 无中生有 ()->结果
     * // function 函数 一个参数一个结果 (参数)->结果 , BiFunction (参数1,参数2)->结果
     * // consumer 消费者 一个参数没结果 (参数)->void, BiConsumer (参数1,参数2)->
     *
     * @param arraySupplier 参数1，提供数组、可以是线程不安全数组或线程安全数组
     * @param arrayFunction 参数2，获取数组长度的方法
     * @param putConsumer   参数3，自增方法，回传 array, index
     * @param printConsumer 参数4，打印数组的方法
     * @param <T>
     */
    public static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> arrayFunction,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer
    ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        Integer length = arrayFunction.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    putConsumer.accept(array, j % length);
                }
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(array);

    }
}
