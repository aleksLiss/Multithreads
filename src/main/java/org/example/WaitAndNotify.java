package org.example;

import java.util.ArrayDeque;

public class WaitAndNotify {
    private static ArrayDeque<Integer> STORAGE = new ArrayDeque<>();

    public int size = 0;
    public synchronized void getFromStorage() {
        while (size < 1) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        size--;
        System.out.printf("Integer from storage: '%d'\n", STORAGE.getFirst());
        STORAGE.removeFirst();
        notifyAll();
    }

    ;

    public synchronized void putIntoStorage() {
        while (size >= 5) {
            try {
                wait();
            } catch (InterruptedException ex) {
                 Thread.currentThread().interrupt();
            }
        }
        size++;
        STORAGE.push(size);
        System.out.printf("Put '%d' into storage\n", size);
        notifyAll();
    }

    static class PutInto implements Runnable {
        WaitAndNotify waitAndNotify;

        public PutInto(WaitAndNotify waitAndNotify) {
            this.waitAndNotify = waitAndNotify;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10; i++) {
                this.waitAndNotify.putIntoStorage();
            }
        }
    }

    static class GetFrom implements Runnable {

        WaitAndNotify waitAndNotify;
        public GetFrom(WaitAndNotify waitAndNotify) {
            this.waitAndNotify = waitAndNotify;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10; i++){
                this.waitAndNotify.getFromStorage();
            }
        }
    }

    public static void main(String[] args) {

        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Thread thread1 = new Thread(new GetFrom(waitAndNotify));
        Thread thread2 = new Thread(new PutInto(waitAndNotify));

        thread1.start();
        thread2.start();
    }
}


