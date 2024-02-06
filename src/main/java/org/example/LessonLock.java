package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public class LessonLock {

    public int VALUE = 0;
    private final Lock lock;
    public LessonLock() {
        this.lock = new ReentrantLock();
    }
    public void incrementVal(){
        this.lock.lock();
        try {
            VALUE++;
            out.printf("Current value '%d' from increment method\n", VALUE);
        }finally {
            this.lock.unlock();
        }
    }

    public void decrementVal(){
        this.lock.lock();
        try {
            VALUE--;
            out.printf("Current value '%d' from decrement method\n", VALUE);
        }
        finally {
            this.lock.unlock();
        }
    }

    public static void main(String[] args) {
        LessonLock lessonLock = new LessonLock();
        Runnable incrementTask = () -> {
            range(0, 10).forEach(i -> lessonLock.incrementVal());
        };
        Runnable decrementTask = () -> {
            range(0, 10).forEach(i -> lessonLock.decrementVal());};


        Thread thread1 = new Thread(incrementTask);
        Thread thread2 = new Thread(decrementTask);

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        out.printf("Current value in the end: '%d'\n", lessonLock.VALUE);
    }


}
