package org.example;

import java.util.concurrent.Semaphore;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.IntStream.range;

public class lessonSemaphore {
    public static void main(String[] args) {
        Cafe cafe = new Cafe(3);

        Thread person1 = new Thread(cafe);
        Thread person2 = new Thread(cafe);
        Thread person3 = new Thread(cafe);
        Thread person4 = new Thread(cafe);
        Thread person5 = new Thread(cafe);
        Thread person6 = new Thread(cafe);

        startEating(person1, person2, person3, person4, person5, person6);
    }

    private static void startEating(Thread ... threads){
        range(0, threads.length)
                .forEach(i -> threads[i].start());
    }
}


class Cafe implements Runnable{
    private final Semaphore semaphore;
    public Cafe(int amountEatingPerson) {
        this.semaphore = new Semaphore(amountEatingPerson);
    }

    @Override
    public void run() {
        try {
            this.semaphore.acquire();
            out.printf("Person %d eating at the table\n", currentThread().getId());
            MILLISECONDS.sleep(1000);
            out.printf("Person %d left cafe\n", currentThread().getId());
            MILLISECONDS.sleep(500);
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }finally {
            this.semaphore.release();
        }
    }
}
