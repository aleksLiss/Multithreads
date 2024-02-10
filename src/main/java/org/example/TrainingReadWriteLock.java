package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.*;
import static java.util.stream.IntStream.range;

public class TrainingReadWriteLock {
    public static void main(String[] args) {

        Shop shop = new Shop();

        Producer producer = new Producer(shop);
        Reader reader = new Reader(shop);

        Thread readThread1 = new Thread(reader);
        Thread readThread2 = new Thread(reader);
        Thread readThread3 = new Thread(reader);
        Thread readThread4 = new Thread(reader);

        Thread writeThread = new Thread(producer);
        writeThread.start();


        startThreads(readThread1, readThread2, readThread3, readThread4);
    }

    private static void startThreads(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
}




class Shop{
    private int counter;
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;
    public Shop() {
        this.counter = 0;
        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = getReadLock();
        this.writeLock = getWriteLock();
    }

    protected Lock getReadLock(){
        return this.readWriteLock.readLock();
    }

    private Lock getWriteLock(){
        return this.readWriteLock.writeLock();
    }

    public void read(){
        this.readLock.lock();
        try {
            System.out.printf("Current products in shop: '%d' read by '%s'\n", this.counter, currentThread().getName());
            MILLISECONDS.sleep(500);
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }
        finally {
            this.readLock.unlock();
        }
    };

    public void put(){
        this.writeLock.lock();
        try {
            this.counter++;
        }
        finally {
            this.writeLock.unlock();
        }
    };

}



class Producer implements Runnable{
    private final Shop shop;
    public Producer(Shop shop) {
        this.shop = shop;
    }
    @Override
    public void run() {
        range(0, 10).forEach(i -> this.shop.put());
    }
}


class Reader implements Runnable{
    private final Shop shop;
    public Reader(Shop shop) {
        this.shop = shop;
    }
    @Override
    public void run() {
        range(0, 10).forEach(i -> this.shop.read());
    }
}