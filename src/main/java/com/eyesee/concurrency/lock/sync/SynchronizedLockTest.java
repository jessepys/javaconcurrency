package com.eyesee.concurrency.lock.sync;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class SynchronizedLockTest {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizedLockTest.class);

    public class ProductPool {
        private Integer product = new Integer(0);

        public synchronized Integer getProduct() {
            return product;
        }

        public synchronized void addOne() {
            this.product = this.product + 1;
        }

        public synchronized void decrease() {
            this.product = this.product - 1;
        }

        public synchronized Integer getNumber() {
            return this.product;
        }
    }

    @Test
    public void testSynchronized() throws InterruptedException {
        ProductPool productPool = new ProductPool();
        CountDownLatch producerCountDownLatch = new CountDownLatch(10);
        CountDownLatch consumerCountDownLatch = new CountDownLatch(10);
        Runnable producer = () -> {
            productPool.addOne();
            producerCountDownLatch.countDown();
        };

        Runnable consumer = () -> {
            productPool.decrease();
            consumerCountDownLatch.countDown();
        };
        IntStream.range(1, 11).forEach(s -> {
            new Thread(producer).start();
        });
        producerCountDownLatch.await();
        assertTrue(productPool.getNumber() == 10);

        IntStream.range(1, 11).forEach(s -> {
            new Thread(consumer).start();
        });
        consumerCountDownLatch.await();

        assertTrue(productPool.getNumber() == 0);
    }



}
