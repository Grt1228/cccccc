package com.unfbx.test.design;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * 保护性暂停模式
 * @Author Grt
 * @Date 2021-08-09
 */
@Slf4j(topic = "c.GuardedObjectTest")
public class GuardedObjectTest {

    public static void main(String[] args) {
        GuardedObject1 guardedObject = new GuardedObject1();

        new Thread(() -> {
            //等待结果
            log.info("等待结果！");
            Object response = guardedObject.getResponse();
            log.info("结果是：{}",response.toString());
        },"T1").start();

        new Thread(() -> {
            log.info("执行操作，获取结果...........");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete("我爱你中国！！！！");
        },"T2").start();
    }

}
class GuardedObject1{

    private Object response;

    public Object getResponse(){
        synchronized (this){
            //没有响应结果，继续等待
            while (response == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
