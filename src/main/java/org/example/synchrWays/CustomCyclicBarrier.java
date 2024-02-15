package org.example.synchrWays;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

public class CustomCyclicBarrier {
    public static final String TEMPLATE_NUMBER_OF_RUNNERS_GROUP = "===<<<%d group had finished>>>===\n";
    public static void main(String[] args) throws InterruptedException{
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new AfterRunning());
        BeforeRunning beforeRunning = new BeforeRunning(cyclicBarrier);

        //First group of runners
        Thread runner1 = new Thread(beforeRunning, "Hank");
        Thread runner2 = new Thread(beforeRunning, "Nick");
        Thread runner3 = new Thread(beforeRunning, "Fred");
        startRunners(runner1, runner2, runner3);
        SECONDS.sleep(1);
        runnersHadFinished(1);
        SECONDS.sleep(2);

        //Second group of runners
        Thread runner4 = new Thread(beforeRunning, "Lulu");
        Thread runner5 = new Thread(beforeRunning, "Linda");
        Thread runner6 = new Thread(beforeRunning, "Franck");
        startRunners(runner4, runner5, runner6);
        SECONDS.sleep(1);
        runnersHadFinished(2);
        SECONDS.sleep(2);

        //Thirds group of runners
        Thread runner7 = new Thread(beforeRunning, "Leon");
        Thread runner8 = new Thread(beforeRunning, "Trump");
        Thread runner9 = new Thread(beforeRunning, "Brian");
        startRunners(runner7, runner8, runner9);
        SECONDS.sleep(1);
        runnersHadFinished(3);;
    }
    private static void startRunners(Thread ... threads){
        range(0, threads.length).forEach(i -> threads[i].start());
    }
    public static void runnersHadFinished(int numberOfGroup){
        out.printf(TEMPLATE_NUMBER_OF_RUNNERS_GROUP, numberOfGroup);
    }

}



class BeforeRunning implements Runnable{
    private final CyclicBarrier cyclicBarrier;
    public static final String TEMPLATE_RUNNER_HAD_STARTED = "%s had started\n";
    public static final String BARRIER_BROKEN_EX = "Barrier had broken\n";
    public BeforeRunning(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run() {
        final String nameRunner = currentThread().getName();
        try {
            out.printf(TEMPLATE_RUNNER_HAD_STARTED, nameRunner);
            cyclicBarrier.await();
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }catch (BrokenBarrierException ex){
            out.println(BARRIER_BROKEN_EX);
        }
    }
}


class AfterRunning implements Runnable{
    public static final String TEMPLATE_RUNNER_HAD_FINISHED = "%s had finished\n";
    @Override
    public void run(){
        final String nameRunner = currentThread().getName();
        out.printf(TEMPLATE_RUNNER_HAD_FINISHED, nameRunner);
    }
}