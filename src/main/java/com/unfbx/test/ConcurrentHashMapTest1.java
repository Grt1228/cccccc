package com.unfbx.test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-17
 */
public class ConcurrentHashMapTest1 {

    /**
     * ConcurrentHashMap 懒惰初始化，在put第一个值时初始化map（Cas保证不会重复创建），大小为64时候才会 链表转红黑树。不到64时候只会扩容数组大小
     * ConcurrentHashMap 存储的key的hash值都为正数，负数另有用途，在扩容是标记节点是否已经扩容移到新的table中
     * ConcurrentHashMap 不能有空的键值，put方法直接跑错NullPointException
     *                  put 当桶下标冲突时候（最后一个else）才会加synchronize同步操作
     *
     *                  //累计map大小时候会附带扩容map
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap(10);
        map.put(1,1);
        System.out.println(map.get(1));
        System.out.println(map);
    }
}
