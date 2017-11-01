package com.eyesee.concurrency.lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SemaphoreTest {
    private static final Logger LOG = LoggerFactory.getLogger(SemaphoreTest.class);
    @Test
    public void testSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(5);
        CountDownLatch countDownLatch = new CountDownLatch(2000);
        Executor executor = Executors.newFixedThreadPool(10);

        Runnable runnable = () -> {
            boolean isAcquired = semaphore.tryAcquire();
            if (isAcquired) {
                try {
                    LOG.info("semaphore is acquired");
                    TimeUnit.MICROSECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    LOG.error("error: {}", ex);
                } finally {
                    semaphore.release();
                }
            } else {
                LOG.info("semaphore is not acquired");
            }
            countDownLatch.countDown();
        };
        IntStream.range(1, 2001).forEach(i ->
                executor.execute(runnable)
        );
        countDownLatch.await();
    }

}
