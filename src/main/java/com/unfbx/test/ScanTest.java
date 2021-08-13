package com.unfbx.test;

import com.unfbx.utils.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-03
 */
public class ScanTest {

    public static void main(String[] args) {
        getAllKeys();
    }

    public static void getAllKeys() {
        Jedis jedis = RedisUtils.getJedis();
        String cursor = ScanParams.SCAN_POINTER_START;
        String key = "*";
        ScanParams scanParams = new ScanParams();
        scanParams.match(key);// 匹配以* 为前缀的 key
        scanParams.count(1000);
        while (true) {
            // 使用scan命令获取500条数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();// 返回0 说明遍历完成
            List<String> list = scanResult.getResult();
            for (String string : list) {
                System.out.println(string + " \t" + jedis.type(string) + " \t" + jedis.ttl(string));
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ("0".equals(cursor)) {
                break;
            }
        }
        System.out.println("ok");
    }
}
