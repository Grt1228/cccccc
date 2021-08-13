package com.unfbx.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * 多种登录方式登录：用户名，手机号，邮箱，只要有一种完成匹配就可以了
 *
 * @Author Grt
 * @Date 2021-08-04
 */
public class UserLogin {

    public static Map<String,String> userListPhone = new HashMap<>();
    public static Map<String,String> userListUsername = new HashMap<>();
    public static Map<String,String> userListEmail = new HashMap<>();

    private String state = State.DEFAULT.name();

    static {
        userListPhone.put("16602111228","123");
        userListEmail.put("453701735@qq.com","123");
        userListUsername.put("fddyd","123");
        userListPhone.put("15679652105","123");
        userListEmail.put("2333@gmail.com","123");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String loginParam = "fddyd";
        UserLogin userLogin = new UserLogin();
        userLogin.login(loginParam);
    }

    public void login(String loginParam) throws ExecutionException, InterruptedException {

        CompletableFuture futures1 = new CompletableFuture();
        CompletableFuture futures2 = new CompletableFuture();
        CompletableFuture futures3 = new CompletableFuture();
        //启动三个线程，分别登录

        //手机号登录线程
        new Thread(() -> {
            try {

                System.out.println("userListPhone》》》》》》》》》》》开始执行");
                System.out.println("userListPhone》》》》》》》》》》》"+getState());
                if(getState().equals(State.SUCCESS.name())){
                    futures1.complete(false);
                    System.out.println("userListPhone》》》》》》》》》》》线程中断");
                    Thread.currentThread().interrupt();
                }else {
                    Thread.sleep(0);
                    if (userListPhone.containsKey(loginParam)) {
                        futures1.complete(true);
                        this.state = State.SUCCESS.name();
                    } else {
                        futures1.complete(false);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        //用户名登录线程
        new Thread(() -> {
            try {
                System.out.println("userListUsername》》》》》》》》》》》开始执行");
                System.out.println("userListUsername》》》》》》》》》》》"+getState());
                if(getState().equals(State.SUCCESS.name())){
                    futures2.complete(false);
                    System.out.println("userListUsername》》》》》》》》》》》线程中断");
                    Thread.currentThread().interrupt();
                }else {
                    if (userListUsername.containsKey(loginParam)) {
                        futures2.complete(true);
                        this.state = State.SUCCESS.name();
                    } else {
                        futures2.complete(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        //邮箱登录线程
        new Thread(() -> {
            try {
                System.out.println("userListEmail》》》》》》》》》》》开始执行");
                System.out.println("userListEmail》》》》》》》》》》》"+getState());
                if(getState().equals(State.SUCCESS.name())){
                    futures3.complete(false);
                    System.out.println("userListEmail》》》》》》》》》》》线程中断");
                    Thread.currentThread().interrupt();
                }else {
                    Thread.sleep(10000);
                    if (userListEmail.containsKey(loginParam)) {
                        futures3.complete(true);
                        this.state = State.SUCCESS.name();

                    } else {
                        futures3.complete(false);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Boolean f1Resp = (Boolean) futures1.get();
        System.out.println("f1Resp："+f1Resp);
        Boolean f2Resp = (Boolean) futures2.get();
        System.out.println("f2Resp："+f2Resp);
        Boolean f3Resp = (Boolean) futures3.get();
        System.out.println("f3Resp："+f3Resp);

        if(f1Resp || f2Resp || f3Resp){
            System.out.println("登录成功了");
        }else {
            System.out.println("登录失败了");
        }
        System.out.println("****主线程执行完毕*****");

    }

    public enum State{
        DEFAULT,
        SUCCESS,
        ERROR
        ;
    }

    public String getState() {
        return state;
    }
}
