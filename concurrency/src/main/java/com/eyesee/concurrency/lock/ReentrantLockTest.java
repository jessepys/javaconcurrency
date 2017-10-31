package com.eyesee.concurrency.lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReentrantLockTest {
    private static final Logger LOG = LoggerFactory.getLogger(ReentrantLockTest.class);

    @Test
    public void testTryLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReentrantLock reentrantLock = new ReentrantLock();
        Runnable runnable = () -> {
            reentrantLock.lock();
            try {
                Thread.sleep(2 * 1000l);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        };

        Runnable interruptRunnable = () -> {
            boolean result = reentrantLock.tryLock();
            if (result) {
                LOG.info("lock success");
                reentrantLock.unlock();
            } else {
                LOG.info("lock failed");
            }
        };

        new Thread(runnable).start();
        new Thread(interruptRunnable).start();
        countDownLatch.await();
    }

    @Test
    public void testLock() throws InterruptedException {
        ProductPool productPool = new ProductPool();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Runnable producer = () -> {
            try {
                productPool.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        };

        range(1, 1001).forEach(s -> new Thread(producer).start());
        countDownLatch.await();
        assertTrue(productPool.getNumber() == 1000);
    }

    public class ProductPool {
        private ReentrantLock  reentrantLock = new ReentrantLock();
        private int number;

        public void addOne() throws InterruptedException {
            reentrantLock.lockInterruptibly();

            try {
                this.number = number + 1;
            } finally {
                reentrantLock.unlock();
            }
        }

        public int getNumber() {
            reentrantLock.lock();
            try {
                return this.number;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Test
    public void testTryLockWithTime() throws InterruptedException, ExecutionException {
        final Lock lock = new ReentrantLock();
        lock.lock();
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> tryLock(lock));
        assertFalse(completableFuture.get().booleanValue());
    }

    private boolean tryLock(Lock lock) {
        try {
            boolean result = lock.tryLock(100, TimeUnit.MILLISECONDS);
            LOG.info("lock result: {}", result);
            return result;
        } catch (InterruptedException e) {
            LOG.error("interrupted: {}", e);
        }
        return false;
    }


    @Test
    public void testLockWithInterrupt() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread childThread = new Thread(() -> {
               lock.lock();
            }, "t1 thread");
        childThread.start();
        childThread.interrupt();

        LOG.info("the child thread state: {}", childThread.getState().name());
        assertFalse(childThread.isInterrupted());
    }

    @Test(expected = InterruptedException.class)
    public void testLockInterruptibly() throws Exception {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread.sleep(1000);
        Thread mainThread = Thread.currentThread();
        Thread t1=new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    LOG.error(" thread interrupted: {}", e);
                    mainThread.interrupt();
                }
            }
        }, "t1 thread");
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        Thread.sleep(1000000);
    }
}
