package com.liujianjian.suanfa;

/**
 * created by liujianjian
 * on 2021/5/29 11:30 上午
 */
public class ThreeThreadOrderExecute {

    private int flag = 0;

    public synchronized void  printa() throws InterruptedException {
        while (true) {
            if (flag == 0) {
                System.out.println("A");
                flag = 1;
                notifyAll();
            }

            wait();
        }
    }

    public synchronized void  printb() throws InterruptedException {
        while (true) {
            if (flag == 1) {
                System.out.println("B");
                flag = 2;
                notifyAll();
            }

            wait();
        }
    }

    public synchronized void  printc() throws InterruptedException {
        while (true) {
            if (flag == 2) {
                System.out.println("C");
                flag = 0;
                notifyAll();
            }

            wait();
        }
    }

    public static void main(String[] args) {
        ThreeThreadOrderExecute execute = new ThreeThreadOrderExecute();
        PrintA printA = new PrintA(execute);
        PrintA printB = new PrintA(execute);
        PrintA printC = new PrintA(execute);

        Thread threadA = new Thread(printA);
        Thread threadB = new Thread(printB);
        Thread threadC = new Thread(printC);

        threadA.start();
        threadB.start();
        threadC.start();

    }

    static class PrintA implements Runnable {
        ThreeThreadOrderExecute orderThread;

        public PrintA(ThreeThreadOrderExecute thread) {
            orderThread = thread;
        }

        @Override
        public void run() {
            try {
                orderThread.printa();
            } catch (InterruptedException e) {
                System.out.println("printA Exception="+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    static class PrintB implements Runnable {
        ThreeThreadOrderExecute orderThread;

        public PrintB(ThreeThreadOrderExecute thread) {
            orderThread = thread;
        }

        @Override
        public void run() {
            try {
                orderThread.printb();
            } catch (InterruptedException e) {
                System.out.println("printB Exception="+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    static class PrintC implements Runnable {
        ThreeThreadOrderExecute orderThread;

        public PrintC(ThreeThreadOrderExecute thread) {
            orderThread = thread;
        }

        @Override
        public void run() {
            try {
                orderThread.printc();
            } catch (InterruptedException e) {
                System.out.println("printC Exception="+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
