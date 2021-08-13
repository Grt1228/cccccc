package com.unfbx.test.design;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * 保护性暂停模式
 * @Author Grt
 * @Date 2021-08-09
 */
@Slf4j(topic = "c.GuardedObjectTimeoutTest")
public class GuardedObjectTimeoutTest {

    public static void main(String[] args) {
        GuardedObject2 guardedObject = new GuardedObject2();

        new Thread(() -> {
            //等待结果
            log.info("等待结果！");
            Object response = guardedObject.getResponse(2000);
            log.info("结果是：{}",response);
        },"T1").start();

        new Thread(() -> {
            log.info("执行操作，获取结果...........");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete(null);
        },"T2").start();
    }

}

//增加超时时间得等待
class GuardedObject2{

    private Object response;

    /**
     * 增加超时时间
     * @param timeout
     * @return
     */
    public Object getResponse(long timeout){
        synchronized (this){
            //开始时间
            long begin = System.currentTimeMillis();
            long passedTime = 0;//已经等待的时间
            while (response == null){
                long waitTime = timeout - passedTime;
                if(waitTime <= 0){
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }


    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();
        }
    }


}
