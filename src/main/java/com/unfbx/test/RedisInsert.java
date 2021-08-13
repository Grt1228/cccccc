package com.unfbx.test;

import com.unfbx.utils.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-02
 */
public class RedisInsert {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();
        for (int i = 0 ;i<10000;i++){
            jedis.set("key"+i, String.valueOf(i));
        }
        jedis.close();
    }




}
