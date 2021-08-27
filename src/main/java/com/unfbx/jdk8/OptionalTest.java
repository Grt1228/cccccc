package com.unfbx.jdk8;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-27
 */
public class OptionalTest {

    public static void main(String[] args) throws IllegalArgumentException{
        User user1 = new User("tom");

        //创建Optional
        Optional<User> optional2 = Optional.ofNullable(user1);

        //map
        String optionalS = optional2.map((u1) -> {
            return u1.getName()+" I love you";
        }).orElse("default");
        System.out.println(optionalS);

        //flatmap
        String aDefault = optional2.flatMap(u2 -> {
            Optional<String> empty = Optional.empty();
            return empty;
        }).orElse("default");
        System.out.println(aDefault);
    }

    private static void methos2() {
        User user1 = new User("tom");
        User user2 = null;
        //创建Optional
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);
        Optional<User> optional1 = Optional.of(user1);
        Optional<User> optional2 = Optional.ofNullable(user2);
        System.out.println(optional1.isPresent());
        System.out.println(optional2.isPresent());
        optional1.ifPresent((user) -> {
            System.out.println(user);
        });
        optional2.ifPresent((user) -> {
            System.out.println(user);
        });


        //ofNullable里面的值不为空的话后面的代码仍然会执行
        User optionalUser1 = Optional.ofNullable(user1).orElse(createdUser());
        //ofNullable里面的值不为空的话后面的代码不会执行
        User optionalUser2 = Optional.ofNullable(user2).orElseGet( () -> createdUser());
        User optionalUser3 = Optional.ofNullable(user1).orElseGet( () -> createdUser());
        System.out.println(optionalUser2.getName());
        System.out.println(optionalUser3.getName());


//        User optionalUser4 = Optional.ofNullable(user2).orElseThrow(() ->
//                new IllegalArgumentException()
//        );
        //map
        String optionalS = optional2.map((u1) -> {
            return  u1.getName();
        }).orElse("default");
        System.out.println(optionalS);
        //flatmap

        String aDefault = optional2.flatMap(User::getOptionalName).orElse("default");
        System.out.println(aDefault);
    }

    public static User createdUser(){
        System.out.println("createdUser do.....");
        return new User("init user");
    }
}


class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Optional<String> getOptionalName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}