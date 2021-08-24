package com.unfbx.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-24
 */
public class JvmGcTest {


    private static final int _128k = 128 * 1024;
    private static final int _512k = 512 * 1024;
    private static final int _1M = 1 * 1024 * 1024;
    private static final int _6M = 6 * 1024 * 1024;
    private static final int _7M = 7 * 1024 * 1024;
    private static final int _8M = 8 * 1024 * 1024;


    /**
     * vm参数设置：
     * -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc
     * @param args
     */
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        list.add(new byte[_7M]);
        list.add(new byte[_7M]);
        list.add(new byte[_1M]);
        list.add(new byte[_1M]);
        list.add(new byte[_512k]);
        list.add(new byte[_128k]);
        list.add(new byte[_128k]);
        list.add(new byte[_128k]);
        list.add(new byte[_128k]);
    }
}
