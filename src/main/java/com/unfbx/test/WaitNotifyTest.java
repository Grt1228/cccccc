package com.unfbx.test;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-06
 */
public class WaitNotifyTest {



}

class GuardedObject{
    /**
     * 唯一表示
     */
    private String id;

    /**
     * 结果
     */
    private Object object;

    public GuardedObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    
}