package com.unfbx.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-13
 */
public class MyAtomicData {
    private volatile int data;

    static Unsafe unsafe;

    static long DATA_OFFSET;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            DATA_OFFSET = unsafe.objectFieldOffset(MyAtomicData.class.getDeclaredField("data"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public MyAtomicData(int data) {
        this.data = data;
    }

    public int getData(){
        return data;
    }

    public void decrease(int amount){
        int oldData;
        while (true){
            //获取旧的数据
            oldData = data;
            //cas操作赋值,返回false已经被其他线程修改
            if (unsafe.compareAndSwapInt(this,DATA_OFFSET,oldData,oldData-amount)) {
                return;
            }
        }
    }
}
