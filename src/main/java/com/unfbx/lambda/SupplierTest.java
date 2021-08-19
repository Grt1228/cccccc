package com.unfbx.lambda;

import java.util.function.Supplier;

/**
 * @Description supplier 提供者 无中生有 ()->结果
 * @Author Grt
 * @Date 2021-08-19
 */
public class SupplierTest {


    public static boolean test1(Supplier<User> user,Supplier<User> otherUser) {

         return otherUser.get() == user.get();

    }

    public static void main(String[] args) {
        boolean b = test1(
                () -> new User("tom", 19),
                () -> new User("other", 18)
        );
        System.out.println(b);
    }
}

class User {

    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
