package com.unfbx.jvm;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-23
 */
public class StringTableTest {
    /**
     * 字符串拼接：如果是常量拼接是在编译器优化的：String str = "a" + "b";
     *          如果是变量拼接1.8使用StringBuilder拼接toString()。String str = a + b;
     * jdk1.8，intern()主动将字符串放入串池中，如果串池中已经存在，不会放入，如果没有则会放入串池。会把串池中的对象返回
     * jdk1.6，intern() 如果不存在会拷贝一份对象放入串池
     * @param args
     */

    /**
     * StringTable位置：
     *                  1.6存储在永久代中。是常量池的一部分
     *                  1.7之后转移到了堆内存中
     *                  原因：永久代回收效率有点低，full gc触发。触发条件是老年代空间不足。
     *                      minor gc 堆内存回收效率高，减少字符串常量对内存的占用。
     * @param args
     */

    /**
     * StringTable垃圾回收：
     *                  StringTable在内存紧张时，会发生垃圾回收
     *
     * @param args
     */

    /**
     * StringTable性能调优：
     *                  串池的实现是StringTable是hashtable实现的。
     *                  1、增加串池的大小（增加hashtable桶的个数）可以减少节点的大小，减少字符串放入串池的时间
     *
     *                  jvm参数调整串池大小：-XX:StringTableSize=xxxx，最小为1009
     *                  2、将字符串放入串池，可以减少jvm中字符串啊常量的个数，减少内存占用
     * @param args
     */
    public static void main(String[] args) {
        method1();
    }

    private static void method2() {
        String str = new String("a") + new String("b");

        String str2 = str.intern();

        String str3 = "ab";

        System.out.println(str == str2);
        System.out.println(str == str3);
        System.out.println(str2 == str3);
        System.out.println(1);
    }

    public static void method1(){
        String s1 = "a";
        String s2 = "b";
        String s3 = "a"+"b"; //编译时优化“ab”
        String s4 = s1+s2;//变量拼接 new String（“ab”）;
        String s5 = "ab";//串池对象
        String s6 = s4.intern();//串池对象

        System.out.println(s3 == s4);//false
        System.out.println(s3 == s5); //true
        System.out.println(s3 == s6);//true
    }
}
