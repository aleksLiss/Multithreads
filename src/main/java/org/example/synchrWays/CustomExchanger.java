package org.example.synchrWays;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CustomExchanger {
    private static final Exchanger<String> exchanger = new Exchanger<>();
    public static void main(String[] args) {

    Thread thread1 = new Thread(new Receiver(exchanger));
    Thread thread2 = new Thread(new Transmitter(exchanger));

    thread1.start();
    thread2.start();

    }
}


class Transmitter implements Runnable{
    private final Exchanger<String> exchanger;
    public Transmitter(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            out.println("Start transmitter...\n");
            SECONDS.sleep(1);
            String result = exchanger.exchange("Message from transmitter to Receiver!!!!");
            out.printf("%s\n", result);
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }
    }
}


class Receiver implements Runnable{
    private final Exchanger<String> exchanger;
    public Receiver(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            out.println("Start receiver...\n");
            SECONDS.sleep(1);
            String result = exchanger.exchange("Message from receiver to Transmitter!!!!");
            out.printf("%s\n", result);
        }catch (InterruptedException ex){
            currentThread().interrupt();
        }
    }
}