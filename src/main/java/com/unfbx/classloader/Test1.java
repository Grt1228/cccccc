package com.unfbx.classloader;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-26
 */
public class Test1 {

    static int i = 10;

    static {
        i = 30;
    }
    static {
        i = 20;
    }
    public static void main(String[] args) {
        System.out.println(i);
    }
}
