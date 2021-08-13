package com.unfbx.test.poll;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-13
 */
@Slf4j(topic = "c.TestPool")
public class TestPool {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2,1000,TimeUnit.MILLISECONDS,10);

        for (int i = 0 ;i <5;i++){
            int j = i +1;
            threadPool.execute(() -> {
                log.info("【"+j+"】"+"任务执行中......");
            });
        }
    }
}

/**
 * 线程池
 */
@Slf4j(topic = "c.ThreadPool")
class ThreadPool{
    //任务队列
    private BlockingQueue<Runnable> taskQueue;
    //线程集合
    private HashSet<Worker> workers = new HashSet();
    //核心线程数
    private int coreSize;
    //获取任务的超时时间，长时间获取不到任务就超时
    private long timeout;
    private TimeUnit timeUnit;

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task){
        //任务数没有超过coreSize时候，直接提交worker执行
        //超过coresize时候，只能加入任务队列阻塞等待
        synchronized (workers){
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);

                log.info("新增worker:{},task:{}",worker,task);
                workers.add(worker);
                worker.start();
            }else {
                log.info("worker满了，加入任务队列，task：{}",task);
                taskQueue.put(task);
            }
        }
    }

    /**
     *
     * @param coreSize
     * @param timeout
     * @param timeUnit
     * @param queueCapCity  任务队列存放的最大任务数
     */
    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int queueCapCity) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapCity);
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //执行任务
            //A：task不为空，直接执行任务
            //B：task执行完毕，尝试从任务队列获取任务执行
            //简单写法=======================================>>>>>开始
            //为空一直等待，不会退出
            //while (task != null || ((task = taskQueue.take()) != null)){
            //增加超时时间，时间超过没有获取到任务就退出
            while (task != null || ((task = taskQueue.poll(timeout,timeUnit)) != null)){
                try {
                    log.info("第一个while正在执行task:{}",task);
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }

            }

            //简单写法=======================================>>>>>结束

            //繁琐写法=======================================>>>>>开始
//            while (task != null){
//                try {
//                    log.info("第一个while正在执行task:{}",task);
//                    task.run();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
//                    task = null;
//                }
//
//            }
//            //为空一直等待，不会退出
//            //task = taskQueue.take();
//            //增加超时时间，时间超过没有获取到任务就退出
//            task = taskQueue.poll(timeout,timeUnit);
//            while (task != null){
//                try {
//                    log.info("第二个while正在执行task:{}",task);
//                    task.run();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
//                    //为空一直等待，不会退出
//                    //task = taskQueue.take();;
//                    //增加超时时间，时间超过没有获取到任务就退出
//                    task = taskQueue.poll(timeout,timeUnit);
//                }
//            }
            //繁琐写法=======================================>>>>>结束

            synchronized (workers){
                log.info("worker移除：{}",this);
                workers.remove(this);
            }
        }
    }
}

/**
 * 阻塞队列 存放任务
 * @param <T>
 */
@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T> {

    //任务队列
    private Deque<T> queue = new ArrayDeque<>();

    //锁对象，多线程访问下获取任务时候保证一个任务被一个线程获取到，避免重复
    //多个生产者想队列存任务，也需要保证线程安全
    private ReentrantLock lock = new ReentrantLock();
    //生产者条件变量，阻塞时候使用
    private Condition fullWaitSet =lock.newCondition();
    //消费者条件变量，同上
    private Condition emptyWaitSet =lock.newCondition();
    //队列容量
    private int capcity;

    public BlockingQueue(int queueCapCity) {

        this.capcity = queueCapCity;
    }

    /**
     * 阻塞获取 有超时的等待
     * @param timeout
     * @param timeUnit
     * @return
     */
    public T poll(long timeout, TimeUnit timeUnit){
        lock.lock();
        try {
            //转换时间 -> 纳秒
            long nanos = timeUnit.toNanos(timeout);
            //不为空时候获取
            while (queue.isEmpty()){
                //消费者等待，因为没有任务需要执行
                try {
                    //等待时间 awaitNanos 返回值 :剩余的时间
                    //（避免虚假唤醒）
                    if(nanos <= 0 ){
                        return null;//超时了
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞获取 一直等待
     * @return
     */
    public T take(){
        lock.lock();
        try {
            //不为空时候获取
            while (queue.isEmpty()){
                //消费者等待，因为没有任务需要执行
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞添加
     * @return
     */
    public void put(T element){
        lock.lock();
        try {
            //任务队列满了的时候，需要阻塞
            while (queue.size() == capcity){
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(element);
            //唤醒消费者
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }

    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }

    }
}
