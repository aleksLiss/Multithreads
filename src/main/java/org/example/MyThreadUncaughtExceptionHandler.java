package org.example;

import static java.lang.Thread.*;

public class MyThreadUncaughtExceptionHandler {

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("Message from thread");
        });


        UncaughtExceptionHandler uncaughtExceptionHandler = (thread, exception) -> {
            System.out.printf("Exception: '%s' from thread: '%s'\n", exception.getMessage(), thread.getName());
        };

        //Method 1
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);

        //Method 2
        thread1.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        thread1.start();


    }
}
