package com.unfbx.test;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-03
 */
public class ThreadTest {

    static volatile int paper = 100;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (paper > 0) {
                    synchronized (ThreadTest.class) {
                        if (paper > 0) {
                            paper--;
                            System.out.println("paper    1==========================>" + paper);
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (paper > 0) {
                    synchronized (ThreadTest.class) {
                        if (paper > 0) {
                            paper--;
                            System.out.println("paper    2==========================>" + paper);
                        }
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (paper > 0) {
                    synchronized (ThreadTest.class) {
                        if (paper > 0) {
                            paper--;
                            System.out.println("paper   3==========================>" + paper);
                        }
                    }
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (paper > 0) {
                    synchronized (ThreadTest.class) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (paper > 0) {
                            paper--;
                            System.out.println("paper    4==========================>" + paper);
                        }
                    }
                }
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                while (paper > 0) {
                    synchronized (ThreadTest.class) {
                        try {
                            wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (paper > 0) {
                            paper--;
                            System.out.println("paper   5==========================>" + paper);
                        }
                    }
                }
            }
        });
        t5.start();
        t4.start();
        t3.start();
        t2.start();
        t1.start();
//        t1.join();
//        t2.join();
        System.out.println("paper==========================>" + paper);
        System.out.println("****主线程执行完毕*****");
    }
}
