package org.example.synchrWays;

import java.util.concurrent.Semaphore;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

public class CustomSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2, true);
        Cafe cafe = new Cafe(semaphore);

        Thread person1 = new Thread(cafe, "Person1");
        Thread person2 = new Thread(cafe, "Person2");
        Thread person3 = new Thread(cafe, "Person3");
        Thread person4 = new Thread(cafe, "Person4");
        Thread person5 = new Thread(cafe, "Person5");

        startThreads(person1, person2, person3, person4, person5);
    }
    private static void startThreads(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
}



class Cafe implements Runnable{
    private final Semaphore semaphore;
    public Cafe(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            out.printf("%s is eating...\n", currentThread().getName());
            SECONDS.sleep(1);
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }finally {
            out.printf("%s left a cafe\n", currentThread().getName());
            semaphore.release();
        }
    }
}