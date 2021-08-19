package com.unfbx.lambda;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-19
 */
public class FunctionTest {

    public static <T> void test1(Supplier <T> name, Function<T, Integer> function) {
        T t = name.get();
        Integer age = function.apply(t);
        System.out.println(age);
    }

    public static void main(String[] args) {
        test1(() -> "tom",
                (name) -> name.length()
        );

        HashMap<String,Integer> map = new HashMap<>();
        map.computeIfAbsent("A",(key) -> {
            return 11111;
        });
        System.out.println(map.get("A"));
    }
}


