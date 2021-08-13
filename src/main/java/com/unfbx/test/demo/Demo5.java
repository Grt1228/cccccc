package com.unfbx.test.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-11
 */
@Slf4j(topic = "c.Demo5")
public class Demo5 {

    public static void main(String[] args) throws InterruptedException {
        GarbageBag bag = new GarbageBag("垃圾装满了");

        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag,true);

        log.info("主线程启动.......");

        GarbageBag prev = ref.getReference();

        log.info(prev.toString());

        new Thread(() -> {
            log.info("打扫卫生的线程开始工作了.......");
            bag.setDesc("垃圾袋置为空......");
            while (!ref.compareAndSet(bag,bag,true,false)){

            }
            log.info(bag.toString());
        }).start();

        Thread.sleep(1000);

        log.info("主线程想换新的垃圾袋.......");

        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);

        log.info("换了么？" + success);
        log.info(ref.getReference().toString());

    }
}

class GarbageBag{


    private String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "desc='" + desc + '\'' +
                '}';
    }


}
