package org.example;

import java.util.stream.IntStream;

public class ThreadWithSynchronized {

    private static int COUNTER1;
    public static int COUNTER2;

    public static final Object obj = new Object();

    //synchronized
    public static void setCOUNTER1() {
        synchronized (ThreadWithSynchronized.class) {
            ThreadWithSynchronized.COUNTER1++;
        }
    }

    //synchronized block
    public static void setCOUNTER2() {
        synchronized (obj) {
            ThreadWithSynchronized.COUNTER2++;
        }
    }

    public static int getCOUNTER1() {
        return COUNTER1;
    }

    public static int getCOUNTER2() {
        return COUNTER2;
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                        ThreadWithSynchronized.setCOUNTER1();
                        ThreadWithSynchronized.setCOUNTER2();
                    }
            );
        });

        Thread t2 = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                ThreadWithSynchronized.setCOUNTER1();
                ThreadWithSynchronized.setCOUNTER2();
            });
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


        System.out.printf("Counter1: %d\n", ThreadWithSynchronized.getCOUNTER1());
        System.out.printf("Counter2: %d\n", ThreadWithSynchronized.getCOUNTER2());


    }
}
