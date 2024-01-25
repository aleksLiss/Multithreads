package org.example;

public class DeadLock {
    private final static Object obj1 = new Object();
    private final static Object obj2 = new Object();
    private static final class Example implements Runnable{
        @Override
        public void run() {
            synchronized (DeadLock.obj1){
                System.out.println("Ex1 obj1");
                synchronized (DeadLock.obj2){
                    System.out.println("Ex1 obj2");
                }
            }
        }
    }
    private static final class Example2 implements Runnable{
        @Override
        public void run() {
            synchronized (DeadLock.obj2){
                System.out.println("EX2 obj1");
                synchronized (DeadLock.obj1){
                    System.out.println("Ex 2 obj 2");
                }
            }
        }
    }
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Example());
        Thread thread2 = new Thread(new Example2());

        thread1.start();
        thread2.start();
    }
}
