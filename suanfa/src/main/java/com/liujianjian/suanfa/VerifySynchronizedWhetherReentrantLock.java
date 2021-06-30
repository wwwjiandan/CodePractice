package com.liujianjian.suanfa;

/**
 * 验证synchronized是否为可重入锁
 * created by liujianjian
 * on 2021/6/30 1:59 下午
 */
public class VerifySynchronizedWhetherReentrantLock extends SuperClass {

    public static void main(String[] args) {
        VerifySynchronizedWhetherReentrantLock test = new VerifySynchronizedWhetherReentrantLock();
        test.doSomeThing();
    }

    public synchronized void doSomeThing() {
        System.out.println("child doSomeThing-"+Thread.currentThread().getId());
        doAnotherThing();
    }

    public synchronized void doAnotherThing() {
        super.doSomeThing();
        System.out.println("child doAnotherThing-"+Thread.currentThread().getId());
    }
}
