package com.eyesee.concurrency.lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class SynchronizedLock {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizedLock.class);

    public class ProductPool {
        private Integer product = new Integer(0);

        public synchronized Integer getProduct() {
            return product;
        }

        public synchronized void addOne() {
            this.product = this.product + 1;
            LOG.info("produce value: {}", this.product);
        }

        public synchronized Integer getOne() {
            Integer old = new Integer(this.product);
            this.product = this.product - 1;
            return old;
        }
    }

    @Test
    public void testSynchronized() throws InterruptedException {
        ProductPool productPool = new ProductPool();
        Runnable producer = () -> {
            productPool.addOne();
        };

        Runnable consumer = () -> {
            Integer one = productPool.getOne();
            LOG.info("consume value: {}", one);
        };

        IntStream.range(1, 11).forEach(s -> {
            new Thread(producer).start();
            new Thread(consumer).start();
        });

        Thread.sleep(2000);
    }



}
