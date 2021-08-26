package com.unfbx.jvm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-25
 */
public class SoftReferenceTest {

    private static final int _4M = 4 * 1024 * 1024;
    private static final int _8M = 8 * 1024 * 1024;


    public static void main(String[] args) {
        method2();

    }

    private static void method2() {
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        //list和SoftReference之间是强引用，softReference和byte数组之间是软引用
        ArrayList<SoftReference<byte[]>> list = new ArrayList<>();
        SoftReference<byte[]> _4MByte1 = new SoftReference<>(new byte[_4M],queue);
        SoftReference<byte[]> _4MByte2 = new SoftReference<>(new byte[_4M],queue);
        SoftReference<byte[]> _4MByte3 = new SoftReference<>(new byte[_4M],queue);
        SoftReference<byte[]> _4MByte4 = new SoftReference<>(new byte[_4M],queue);
        SoftReference<byte[]> _4MByte5 = new SoftReference<>(new byte[_4M],queue);
        list.add(_4MByte1);
        list.add(_4MByte2);
        list.add(_4MByte3);
        list.add(_4MByte4);
        list.add(_4MByte5);

        //遍历引用队列，如果有元素，则移除
        Reference<? extends byte[]> poll = queue.poll();
        while(poll != null) {
            //引用队列不为空，则从集合中移除该元素
            list.remove(poll);
            //移动到引用队列中的下一个元素
            poll = queue.poll();
        }
        for (int i = 0 ;i<list.size();i++){
            System.out.println(list.get(i).get());
        }
    }

    private static void method1() {
        //list和SoftReference之间是强引用，softReference和byte数组之间是软引用
        ArrayList<SoftReference<byte[]>> list = new ArrayList<>();
        SoftReference<byte[]> _4MByte1 = new SoftReference<>(new byte[_4M]);
        SoftReference<byte[]> _4MByte2 = new SoftReference<>(new byte[_4M]);
        SoftReference<byte[]> _4MByte3 = new SoftReference<>(new byte[_4M]);
        SoftReference<byte[]> _4MByte4 = new SoftReference<>(new byte[_4M]);
        SoftReference<byte[]> _4MByte5 = new SoftReference<>(new byte[_4M]);
        list.add(_4MByte1);
        list.add(_4MByte2);
        list.add(_4MByte3);
        list.add(_4MByte4);
        list.add(_4MByte5);
        for (int i = 0 ;i<list.size();i++){
            System.out.println(list.get(i).get());
        }
    }
}
