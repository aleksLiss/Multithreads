package org.example;

import java.util.concurrent.CountDownLatch;
import static java.lang.Thread.currentThread;
import static java.util.stream.IntStream.range;

public class lessonCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        Counter1 counter1 = new Counter1(latch);
        Counter2 counter2 = new Counter2(latch);
        Counter3 counter3 = new Counter3(latch);

        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);
        Thread thread3 = new Thread(counter3);

        startThreads(thread1, thread2, thread3);

        try {
            System.out.println("Latch is waiting...");
            latch.await();
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }finally {
            System.out.println("All threads have done theirs work");
        }
    }

    private static void startThreads(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
}



class Counter1 implements Runnable{

    private final CountDownLatch latch;

    Counter1(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        range(0, 10).forEach(i -> System.out.printf("Current number: %d counted by thread %s\n",
                i,
                currentThread().getName()));
        latch.countDown();
    }
}
class Counter2 implements Runnable{
    private final CountDownLatch latch;

    Counter2(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        range(10, 20).forEach(i -> System.out.printf("Current number: %d counted by thread: %s\n",
                i,
                currentThread().getName()));
        latch.countDown();
    }
}

class Counter3 implements Runnable{

    private final CountDownLatch latch;

    Counter3(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        range(20, 31).forEach(i -> System.out.printf("Current number: %d counted by thread: %s\n",
                i,
                currentThread().getName()));
        latch.countDown();
    }
}
