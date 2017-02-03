package com.eyesee.concurrency.context;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Created by jessepi on 2/3/17.
 */
public class Switch {

    /**
     * Use to monitor the system context switch.
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        IntStream.range(1, 1000000).forEach(s -> {
            Runnable runnable = () -> {
                System.out.println("begin thread i" + s);
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            CompletableFuture.runAsync(runnable);

        });

        Thread.sleep(20000000L);

    }

}
