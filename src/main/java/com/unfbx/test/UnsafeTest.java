package com.unfbx.test;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-13
 */
@Slf4j(topic = "c.UnsafeTest")
public class UnsafeTest {

    static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static Unsafe getUnsafe(){
        return unsafe;
    }

    public static void main(String[] args) {
        System.out.println(unsafe);


    }
}
