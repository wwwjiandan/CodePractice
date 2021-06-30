package com.liujianjian.suanfa;

/**
 * created by liujianjian
 * on 2021/6/30 2:01 下午
 */
public class SuperClass {
    public synchronized void doSomeThing() {
        System.out.println("father doSomeThing-"+Thread.currentThread().getId());
    }
}
