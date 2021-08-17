package com.unfbx.test;

import com.unfbx.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-17
 */
@Slf4j(topic = "c.RedisUnlockTest")
public class RedisUnlockTest {
    
    //解锁不是原子性操作导致的原因
    //t1线程解锁时候刚好t1线程持有锁过期自动失效
    //t2线程拿到锁，t1线程继续向下del(key)删除了t2线程的锁
    //t2线程获取到锁
    //异常就出现了

    public static final String productId = "product_00001";
    public static int count = 2;
    private static final String LOCK_SUCCESS = "OK";


    public static void main(String[] args) {

        new Thread(() -> {
            Jedis jedis = RedisUtils.getJedis();
            String uuid = uuid();
            try {
                while (true) {
                    SetParams params = new SetParams().nx().px(5000L);
                    String lock = jedis.set(productId, uuid, params);
                    if (LOCK_SUCCESS.equals(lock) ) {
                        log.info("T1 lock ：{}", lock);
                        log.info("T1 do start ..................");
                        Thread.sleep(8000);
                        log.info("T1 do end ..................");
                        break;
                    } else {
                        Thread.sleep(10);
                    }
                }
            } catch (Exception e) {
                log.error("发生异常........");
            } finally {
                if (uuid.equals(jedis.get(productId))) {
                    Long unlock = jedis.del(productId);
                    log.info("T1 unlock ：{}", unlock);
                }

            }
        }, "T1").start();

        new Thread(() -> {
            Jedis jedis = RedisUtils.getJedis();
            String uuid = uuid();
            try {
                while (true) {
                    SetParams params = new SetParams().nx().px(5000L);
                    String lock = jedis.set(productId, uuid, params);
                    if (LOCK_SUCCESS.equals(lock) ) {
                        log.info("T2 lock ：{}", lock);
                        log.info("T2 do start ..................");
                        Thread.sleep(8000);
                        log.info("T1 do end ..................");
                        break;
                    } else {
                        Thread.sleep(10);
                    }
                }
            } catch (Exception e) {
                log.error("发生异常........");
            } finally {
                if (uuid.equals(jedis.get(productId))) {
                    Long unlock = jedis.del(productId);
                    log.info("T2 unlock ：{}", unlock);
                }
            }
        }, "T2").start();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
