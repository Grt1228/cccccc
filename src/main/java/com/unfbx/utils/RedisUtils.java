package com.unfbx.utils;

import redis.clients.jedis.Jedis;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-03
 */
public class RedisUtils {

    public static Jedis getJedis() {
        Jedis jedis = new Jedis("localhost", 6379);  //指定Redis服务Host和port
        return jedis;
    }

    public static void closeJedis(Jedis jedis){
        jedis.close();
    }
}
