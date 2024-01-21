package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CreateThreads {

    public static void main(String[] args) {
        ThreadExtThread thread1 = new ThreadExtThread();
        thread1.start();

        ThreadImplRunnable threadImplRunnable = new ThreadImplRunnable();
        Thread thread2 = new Thread(threadImplRunnable);
        thread2.start();


        ThreadFactory myThreadFactory = new MyThreadFactory();
        Thread thread3 = myThreadFactory.newThread(new ThreadImplRunnable());
        thread3.start();


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new ThreadImplRunnable());


        joinThread(thread1, thread2, thread3);
    }

    private static final class ThreadExtThread extends Thread{

        public void run(){
            System.out.printf("Hello from '%s', that have been extended Thread.\n",Thread.currentThread().getName());
        }
    }

    private static final class ThreadImplRunnable implements Runnable{

        @Override
        public void run() {
            System.out.printf("Hello from '%s', that have been implemented Runnable.\n",Thread.currentThread().getName());
        }
    }

    private static final class MyThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable);
        };
    }


    private static void joinThread(Thread ... threads){
        for(Thread thread: threads){
            try {
                thread.join();
            }catch (InterruptedException ex){
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted");
            }
        }
    }
}
