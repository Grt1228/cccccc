package com.unfbx.demo;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description 多种登录方式登录：用户名，手机号，邮箱，只要有一种完成匹配就可以了
 * @Author Grt
 * @Date 2021-08-04
 */
@Slf4j(topic = "c.UserLoginPool")
public class UserLoginPool {

    public static Map<String, String> userListPhone = new HashMap<>();
    public static Map<String, String> userListUsername = new HashMap<>();
    public static Map<String, String> userListEmail = new HashMap<>();

    static {
        userListPhone.put("16602111228", "123");
        userListEmail.put("453701735@qq.com", "123");
        userListUsername.put("fddyd", "123");
        userListPhone.put("15679652105", "123");
        userListEmail.put("2333@gmail.com", "123");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String loginParam = "166021112281";
        UserLoginPool userLogin = new UserLoginPool();
        userLogin.login(loginParam);
    }

    public static long random() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(1000);
    }


    public void login(String loginParam) throws  InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(1);
        //启动三个线程，分别登录
        Future<Boolean> submit1 = executorService.submit(() -> {
            if (userListPhone.containsKey(loginParam)) {
                log.info("手机号登录线程running");
                Thread.sleep(random());
                latch.countDown();
                return true;
            }
            return false;
        });
        //启动三个线程，分别登录
        Future<Boolean> submit2 = executorService.submit(() -> {
            if (userListEmail.containsKey(loginParam)) {
                log.info("邮箱登录线程running");
                Thread.sleep(random());
                latch.countDown();
                return true;
            }
            return false;
        });
        //启动三个线程，分别登录
        Future<Boolean> submit3 = executorService.submit(() -> {
            if (userListUsername.containsKey(loginParam)) {
                log.info("用户名登录线程running");
                Thread.sleep(random());
                latch.countDown();
                return true;
            }
            return false;
        });
        //暂未实现，三个线程都失败的情况
        latch.await();
        if (true) {
            log.info("****登录成功*****");
        } else {
            log.info("****登录失败*****");
        }


        log.info("****主线程执行完毕*****");
        executorService.shutdown();
    }
}
