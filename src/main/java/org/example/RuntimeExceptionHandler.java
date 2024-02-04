package org.example;

public class RuntimeExceptionHandler implements Runnable{


    @Override
    public void run() {
        throw new RuntimeException("Message");
    }

    public static void main(String[] args) {
        RuntimeExceptionHandler runtimeExceptionHandler = new RuntimeExceptionHandler();
        Thread thread1 = new Thread(runtimeExceptionHandler);


        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (thread, ex) -> {
            System.out.printf("Error '%s' from thread '%s'\n", ex.getMessage(), thread.getName());
        };

        //Method 1
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);

        //Method 2
        thread1.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        thread1.start();
    }
}
