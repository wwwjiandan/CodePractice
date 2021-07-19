/**
 * Copyright (c) 2013-2015 YunZhongXiaoNiao Tech
 */
package com.liujianjian.suanfa;

/**
 * author liujianjian on 2021/7/16
 *        08:13.
 *        验证虚拟机栈中的对象作为GCRoot对象
 */
public class GCRootVirtualMachineStackObject {

	private int _10MB = 10 * 1024 * 1024;
	private byte [] memory = new byte[8 * _10MB];

	public static void main(String[] args) {
		System.out.println("开始时：");
		printMemory();

		method();

		System.gc();


		System.out.println("第二次GC完成");

		printMemory();


	}

	public static void printMemory() {
		 System.out.print("free is "+Runtime.getRuntime().freeMemory()/1024/1024+" M,  ");
		 System.out.print("total is "+Runtime.getRuntime().totalMemory()/1024/1024+" M\n");
	}

	public static void method(){
		GCRootVirtualMachineStackObject g = new GCRootVirtualMachineStackObject();
		System.gc();
		System.out.println("第一次GC完成");

		printMemory();

	}

}
