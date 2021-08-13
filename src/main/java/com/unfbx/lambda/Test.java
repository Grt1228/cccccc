package com.unfbx.lambda;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-12
 */
public class Test {

    public static int test(LambdaTest test, int i) {
        return test.dealWith(i);
    }

    public static void main(String[] args) {
        //test2();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalAccessor parse = formatter.parse("2021-01-01");
        System.out.println(parse);
        String a = A.a;
    }

    private static void test2() {
        System.out.println(test(x -> x * 10, 10));
    }

    public static class A{
        public static  String a = "";
    }
}
