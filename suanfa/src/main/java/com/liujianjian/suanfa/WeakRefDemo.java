package com.liujianjian.suanfa;

import java.lang.ref.WeakReference;

/**
 * created by liujianjian
 * on 2021/5/29 7:26 上午
 */
public class WeakRefDemo {

    static class BigObject{

    }

    public static void main(String[] args) {
        WeakReference<BigObject> reference = new WeakReference<>(new BigObject());

        System.out.println("before gc, refernce.get="+reference.get());

        System.gc();

        System.out.println("after gc, refernce.get="+reference.get());
    }
}
