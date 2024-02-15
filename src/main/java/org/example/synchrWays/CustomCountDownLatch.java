package org.example.synchrWays;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.IntStream.range;

public class CustomCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(3);
        WaitingRunner waitingRunner = new WaitingRunner(count);

        Thread runner1 = new Thread(waitingRunner, "Jho");
        Thread runner2 = new Thread(waitingRunner, "Mia");
        Thread runner3 = new Thread(waitingRunner, "Lila");

        startThreads(runner1, runner2, runner3);
    }
    private static void startThreads(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
}


class WaitingRunner implements Runnable{

    private static final String TEMPLATE_RUNNER_BEFORE_START = "%s went to start-line\n";
    private static final String TEMPLATE_RUNNER_AFTER_START = "%s started\n";
    private final CountDownLatch countDownLatch;
    public WaitingRunner(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.countDown();
            out.printf(TEMPLATE_RUNNER_BEFORE_START, currentThread().getName());
            MILLISECONDS.sleep(500);
            countDownLatch.await();
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }finally {
            out.printf(TEMPLATE_RUNNER_AFTER_START, currentThread().getName());
        }
    }
}
