/**
 * Copyright (c) 2013-2015 YunZhongXiaoNiao Tech
 */
package com.liujianjian.suanfa;

/**
 * author liujianjian on 2021/7/20
 *        06:25.
 */
public class StopThread implements Runnable{
	@Override
	public void run() {
		int count = 0;
		while (Thread.currentThread().isInterrupted() && count < 1000) {
			System.out.println("count="+count++);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new StopThread());
		thread.start();
		Thread.sleep(5);
		thread.interrupt();
	}
}
