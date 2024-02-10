package org.example;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

public class lessonVolatile {
    public static void main(String[] args) throws InterruptedException{

        Counter counter = new Counter();

        Thread thread1 = new Thread(counter);
        Thread thread2 = new Thread(counter);
        Thread thread3 = new Thread(counter);
        Thread thread4 = new Thread(counter);

        startThread(thread1, thread2, thread3, thread4);

        SECONDS.sleep(5);

        counter.setFlag();
    }

    private static void startThread(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
}


class Counter implements Runnable{
    private volatile boolean flag;
    private int count;
    public Counter() {
        this.flag = true;
        this.count = 0;
    }

    public void setFlag(){
        this.flag = false;
    }

    private synchronized void incrementCounter(){
        count++;
    }

    @Override
    public void run() {
        try {
        while (this.flag){
            incrementCounter();
            out.printf("Current count: %d incremented by '%s'\n", this.count, currentThread().getName());
            MILLISECONDS.sleep(100);
        }
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }

    }
}