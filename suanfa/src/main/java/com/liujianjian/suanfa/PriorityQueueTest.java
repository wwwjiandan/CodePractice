package com.liujianjian.suanfa;

import java.util.PriorityQueue;

/**
 * created by liujianjian
 * on 2021/5/29 1:56 下午
 */
public class PriorityQueueTest {

    PriorityQueue priorityQueue = new PriorityQueue();

    public static int maxKNumber(int num[], int k) {
        int number = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue();
        for (int key : num) {
            priorityQueue.add(key);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }

        return number;
    }
}
