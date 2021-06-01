package com.liujianjian.suanfa;

/**
 * created by liujianjian
 * on 2021/5/30 4:23 下午
 */
public class ThreadOrderExecuteByNotifyWait {

    private volatile int flag = 1;
    private static volatile int number = 1;


    private final Object object = new Object();

    public ThreadOrderExecuteByNotifyWait() {

    }

    static class MyThread implements Runnable {
        public int textPrint;



        @Override
        public void run() {
            System.out.println(number);
            number++;
        }

        public MyThread(int text) {
            textPrint = text;
        }
    }

    public static void main(String[] args) {
        ThreadOrderExecuteByNotifyWait threadOrderExecuteByNotifyWait = new ThreadOrderExecuteByNotifyWait();



        MyThread threadA = new MyThread(number);
        MyThread threadB = new MyThread(number);
        MyThread threadC = new MyThread(number);

        for (int i=0;i<10;i++) {
            try {
                threadOrderExecuteByNotifyWait.first(threadA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                threadOrderExecuteByNotifyWait.second(threadB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                threadOrderExecuteByNotifyWait.third(threadC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void first(Runnable first) throws InterruptedException {
        synchronized (object) {
            while (flag != 1)
                object.wait();
            first.run();
            flag = 2;
            object.notifyAll();
        }
    }

    public void second(Runnable second) throws InterruptedException {
        synchronized (object) {
            while (flag != 2)
                object.wait();
            second.run();
            flag = 3;
            object.notifyAll();
        }
    }

    public void third(Runnable third) throws InterruptedException {
        synchronized (object) {
            while (flag != 3)
                object.wait();
            third.run();
            flag = 1;
            object.notifyAll();
        }
    }
}
