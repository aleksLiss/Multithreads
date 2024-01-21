package org.example;

import java.util.concurrent.*;

public class MyThreadWithCallable {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Long> sub = executorService.submit(new MyCall());
        try {
            Long id = sub.get();
            System.out.println(id);
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace(System.out);
        } finally {
            executorService.shutdown();
        }

    }


    private static final class MyCall implements Callable<Long> {
        @Override
        public Long call() {
            try {
                return Thread.currentThread().getId();
            } catch (RuntimeException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

}
