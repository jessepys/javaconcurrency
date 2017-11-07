package com.eyesee.concurrency.lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CyclicBarrierTest {
    private static final Logger LOG = LoggerFactory.getLogger(CyclicBarrierTest.class);

    @Test
    public void testCyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            LOG.info("end of cyclic barrier");
        });
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Supplier<Integer> supplier = () -> {
            LOG.info("enter callable");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            return 1;
        };
        IntStream.range(1, 3).forEach(s -> {
            CompletableFuture.supplyAsync(supplier);
        });

        countDownLatch.await();
    }
}
