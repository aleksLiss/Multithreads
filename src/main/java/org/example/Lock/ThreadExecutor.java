package org.example.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadExecutor {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CustomExecutor customExecutor = new CustomExecutor();
        Future<Integer> res = executorService.submit(customExecutor);
        System.out.printf("Answer from single task: %d\n",res.get());

        List<Future<Integer>> taskList = new ArrayList<>();

        IntStream.range(0, 3).forEach(x -> {
            CustomExecutor task = new CustomExecutor();
            Future<Integer> result = executorService.submit(task);
            taskList.add(result);
        });

        taskList.stream().forEach(x -> {
            try {
                System.out.printf("Answer from multiple tasks: %d\n",x.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.shutdown();
    }

    public static final class CustomExecutor implements Callable<Integer> {

        @Override
        public Integer call() {
            return new Random().nextInt();
        }
    }
}
